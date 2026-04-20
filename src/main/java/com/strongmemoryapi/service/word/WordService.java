package com.strongmemoryapi.service.word;

import com.strongmemoryapi.domain.exception.local.InsufficientWordsException;
import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.word.WordRegistrationRequest;
import com.strongmemoryapi.dto.request.word.WordUpdateRequest;
import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.domain.entity.word.WordEntity;
import com.strongmemoryapi.repository.word.WordRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private DifficultyService difficultyService;

    @Autowired
    private WordCacheService cacheService;

    @CacheEvict(value = "wordIdsByDifficulty", allEntries = true)
    public WordEntity register(WordRegistrationRequest request){
        if(wordRepository.existsByWord(request.word())){
            throw new ResourceAlreadyExistsException("Palavra já cadastrada.");
        }

        DifficultyEntity difficulty = difficultyService.findByDifficultyName(request.difficulty());

        WordEntity word = new WordEntity();
        word.setWord(request.word().toLowerCase());
        word.setDifficulty(difficulty);

        return wordRepository.save(word);
    }

    @CacheEvict(value = "wordIdsByDifficulty", allEntries = true)
    public void update(Long id, WordUpdateRequest request){
        WordEntity word = wordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palavra não encontrada."));

        word.setWord(request.word().toLowerCase());
        wordRepository.save(word);
    }

    @CacheEvict(value = "wordIdsByDifficulty", allEntries = true)
    public void delete(Long id){
        if(!wordRepository.existsById(id)){
           throw new ResourceNotFoundException("Palavra não encontrada.");
        }
        wordRepository.deleteById(id);
    }

    public List<WordEntity> findRandomWords(String difficulty, int quantityWords){
        if(quantityWords < 2){
            throw new IllegalArgumentException("Quantidade de palavras inválida para sorteio.");
        }

        DifficultyEntity currentDifficulty = difficultyService.findByDifficultyName(difficulty);

        List<Long> wordsIds = new ArrayList<>(cacheService.findIdsByDifficulty(currentDifficulty));

        int totalWords = wordsIds.size();
        if(quantityWords > totalWords){
            throw new InsufficientWordsException();
        }

        Collections.shuffle(wordsIds);
        List<Long> selectedIds = wordsIds.subList(0, quantityWords);

        return wordRepository.findByIdIn(selectedIds);
    }

    public Page<WordEntity> findByDifficulty(
            String difficulty,
            int page,
            int size,
            String sortBy
    ){
        DifficultyEntity currentDifficulty = difficultyService.findByDifficultyName(difficulty);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return wordRepository
                .findAllByDifficulty(pageable, currentDifficulty);
    }

    public void checkAlreadyExistsByWord(String word, String exceptionMessage){
        if(!wordRepository.existsByWord(word)) return;
        throw new ResourceAlreadyExistsException(exceptionMessage);
    }

}