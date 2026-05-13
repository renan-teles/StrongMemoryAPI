package com.strongmemoryapi.service.word;

import com.strongmemoryapi.domain.exception.local.InsufficientWordsException;
import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.PaginationDTO;
import com.strongmemoryapi.dto.word.RegisterWordRequest;
import com.strongmemoryapi.dto.word.UpdateWordRequest;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.word.WordModel;
import com.strongmemoryapi.repository.word.WordRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import com.strongmemoryapi.utils.mapper.PageableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private final String ALREADY_EXISTS_MESSAGE = "Palavra já existente.";

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordCacheService cacheService;

    @Autowired
    private DifficultyService difficultyService;

    @CacheEvict(value = "wordIdsByDifficulty", allEntries = true)
    public WordModel register(RegisterWordRequest request){
        String wordStr = request.word().toLowerCase();
        String difficultyName = request.difficulty();

        DifficultyModel difficulty = difficultyService.findByName(difficultyName);

        Optional<WordModel> existing = wordRepository.findByWord(wordStr);
        if (existing.isPresent()) {
            WordModel word = existing.get();

            if (word.getDeleted()){
                if(!word.getDifficulty().getName().equalsIgnoreCase(difficultyName)){
                    word.setDifficulty(difficulty);
                }
                return reactivate(word);
            }

            throw new ResourceAlreadyExistsException(ALREADY_EXISTS_MESSAGE);
        }

        return register(request, difficulty);
    }

    private WordModel register(RegisterWordRequest request,DifficultyModel difficulty){
        WordModel word = new WordModel();
        word.setWord(request.word().toLowerCase());
        word.setDifficulty(difficulty);
        word.setDeleted(false);
        word.setDeletedAt(null);

        try {
            return wordRepository.save(word);
        } catch(DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isForeignKeyViolation(ex)){
                throw new ResourceNotFoundException("Dificuldade não encontrada.");
            }
            throw ex;
        }
    }

    @CacheEvict(value = "wordIdsByDifficulty", allEntries = true)
    public void update(Long id, UpdateWordRequest request){
        WordModel word = findByIdAndDeletedFalse(id);
        word.setWord(request.word().toLowerCase());

        try {
            wordRepository.save(word);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isUniqueConstraintViolation(ex)){
                throw new ResourceAlreadyExistsException(ALREADY_EXISTS_MESSAGE);
            }
            throw ex;
        }
    }

    @CacheEvict(value = "wordIdsByDifficulty", allEntries = true)
    public void delete(Long id){
        WordModel word = findByIdAndDeletedFalse(id);
        word.setDeleted(true);
        word.setDeletedAt(Instant.now());

        wordRepository.save(word);
    }

    public Page<WordModel> findByDifficulty(
            String difficulty,
            PaginationDTO pagination
    ){
        Pageable pageable = PageableMapper.toPageable(pagination);
        return wordRepository
                .findByDifficulty_NameAndDeletedFalse(difficulty, pageable);
    }

    public List<Long> findRandomWordIds(String difficultyName, int quantityWords){
        if(quantityWords < 2){
            throw new IllegalArgumentException("Quantidade de palavras inválida para sorteio.");
        }

        List<Long> wordIds = new ArrayList<>(
             cacheService.findIdsByDifficultyName(difficultyName.toLowerCase())
        );

        if(wordIds.isEmpty()){
            throw new ResourceNotFoundException("Palavras não encontradas para sorteio.");
        }
        if(quantityWords > wordIds.size()){
            throw new InsufficientWordsException();
        }

        Collections.shuffle(wordIds);
        return wordIds.subList(0, quantityWords);
    }

    private WordModel reactivate(WordModel word) {
        word.setDeletedAt(null);
        word.setDeleted(false);
        return wordRepository.save(word);
    }

    public void assertIfAlreadyExistsByWord(String word, String exceptionMessage){
        if(!wordRepository.existsByWord(word)) return;
        throw new ResourceAlreadyExistsException(exceptionMessage);
    }

    private WordModel findByIdAndDeletedFalse(Long id){
        return wordRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palavra não encontrada."));
    }

}