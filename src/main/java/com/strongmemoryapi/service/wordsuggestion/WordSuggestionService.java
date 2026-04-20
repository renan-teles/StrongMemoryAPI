package com.strongmemoryapi.service.wordsuggestion;

import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.wordsuggestion.WordSuggestionRequest;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.domain.entity.wordsuggestion.WordSuggestionEntity;
import com.strongmemoryapi.repository.wordsuggestion.WordSuggestionRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.service.user.UserService;
import com.strongmemoryapi.service.word.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class WordSuggestionService {

    @Autowired
    private WordSuggestionRepository suggestionRepository;

    @Autowired
    private DifficultyService difficultyService;

    @Autowired
    private UserService userService;

    @Autowired
    private WordService wordService;

    public Page<WordSuggestionEntity> findAll(
            int page,
            int size,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return suggestionRepository.findAll(pageable);
    }

    public Page<WordSuggestionEntity> findByPeriod(
            LocalDate startDate,
            LocalDate endDate,
            int page,
            int size
    ) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Data final não pode ser menor que a data inicial.");
        }

        Pageable pageable = PageRequest.of(page, size);

        Instant start = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = endDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        return suggestionRepository.findBySuggestedAtBetween(start, end, pageable);
    }

    public WordSuggestionEntity register(
            Long userId,
            WordSuggestionRequest request
    ){
        UserEntity user = userService.findById(
                userId,
                "Usuário sugeridor não encontrado."
        );

        wordService.checkAlreadyExistsByWord(
                request.suggestedWord(),
                "Palavra sugerida já cadastrada."
        );

        if(suggestionRepository.existsBySuggestedWord(request.suggestedWord())){
            throw new ResourceAlreadyExistsException("Palavra já sugerida.");
        }

        difficultyService.checkExistsByDifficulty(request.suggestedDifficulty());

        WordSuggestionEntity suggestion = new WordSuggestionEntity();
        suggestion.setUser(user);
        suggestion.setSuggestedWord(request.suggestedWord().toLowerCase());
        suggestion.setSuggestedDifficulty(request.suggestedDifficulty().toLowerCase());

        return suggestionRepository.save(suggestion);
    }

    public void delete(Long id){
        if(!suggestionRepository.existsById(id)){
            throw new ResourceNotFoundException("Sugestão de palavra não encontrada.");
        }
        suggestionRepository.deleteById(id);
    }

}
