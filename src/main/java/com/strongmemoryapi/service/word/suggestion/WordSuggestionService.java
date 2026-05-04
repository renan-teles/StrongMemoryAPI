package com.strongmemoryapi.service.word.suggestion;

import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.dto.FindByPeriodWordSuggestionDTO;
import com.strongmemoryapi.dto.PaginationDTO;
import com.strongmemoryapi.dto.request.wordsuggestion.RegisterWordSuggestionRequest;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.domain.model.WordSuggestionModel;
import com.strongmemoryapi.repository.WordSuggestionRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.service.word.WordService;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import com.strongmemoryapi.utils.mapper.PageableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class WordSuggestionService {

    private final String NOT_FOUND_MESSAGE = "Sugestão de palavra não encontrada.";

    @Autowired
    private WordSuggestionRepository repository;

    @Autowired
    private WordService wordService;

    @Autowired
    private DifficultyService difficultyService;

    public Page<WordSuggestionModel> findAll(PaginationDTO pagination, Optional<String> difficultyOpt) {
        Pageable pageable = PageableMapper.toPageable(pagination);

        if(difficultyOpt.isPresent()){
            String difficulty = difficultyOpt.get();
            return repository
                    .findByDifficulty_NameAndDeletedFalse(difficulty, pageable);
        }

        return repository.findByDeletedFalse(pageable);
    }

    public Page<WordSuggestionModel> findByPeriod(
            FindByPeriodWordSuggestionDTO findData,
            PaginationDTO pagination
    ) {
        LocalDate startDate = findData.startDate();
        LocalDate endDate = findData.endDate();

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Data final não pode ser menor que a data inicial.");
        }

        Pageable pageable = PageableMapper.toPageable(pagination);

        Instant start = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = endDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        Optional<String> difficultyOpt = findData.difficulty();

        if(difficultyOpt.isPresent()){
           String difficulty = difficultyOpt.get();
           return repository
                   .findByDifficulty_NameAndSuggestedAtBetweenAndDeletedFalse(
                           difficulty, start, end, pageable
                   );
        }

        return repository
                .findBySuggestedAtBetweenAndDeletedFalse(start, end, pageable);
    }

    public WordSuggestionModel register(Long userId, RegisterWordSuggestionRequest request){
        wordService.assertIfAlreadyExistsByWord(
               request.word(),
               "Palavra sugerida já cadastrada."
        );

        DifficultyModel difficulty = difficultyService
                .findByName(request.difficulty().toLowerCase());

        UserModel user = new UserModel();
        user.setId(userId);

        WordSuggestionModel suggestion = new WordSuggestionModel();
        suggestion.setUser(user);
        suggestion.setDifficulty(difficulty);
        suggestion.setWord(request.word().toLowerCase());
        suggestion.setDeleted(false);
        suggestion.setDeletedAt(null);

        try {
            return repository.save(suggestion);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isUniqueConstraintViolation(ex)){
                throw new ResourceAlreadyExistsException("Palavra já sugerida.");
            }
            if(DatabaseErrorUtils.isForeignKeyViolation(ex)){
                throw new ResourceNotFoundException("Usuário não encontrado");
            }
            throw ex;
        }
    }

    public void delete(Long id){
        WordSuggestionModel suggestion = repository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));

        delete(suggestion);
    }

    public void delete(String word){
        WordSuggestionModel suggestion = repository
                .findByWordAndDeletedFalse(word)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));

        delete(suggestion);
    }

    private void delete(WordSuggestionModel suggestion){
        suggestion.setDeleted(true);
        suggestion.setDeletedAt(Instant.now());
        repository.save(suggestion);
    }

}

