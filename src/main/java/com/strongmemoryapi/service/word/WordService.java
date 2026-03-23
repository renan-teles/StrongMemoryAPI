package com.strongmemoryapi.service.word;

import com.strongmemoryapi.exception.local.InsufficientWordsException;
import com.strongmemoryapi.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.word.WordRegistrationRequest;
import com.strongmemoryapi.dto.request.word.WordUpdateRequest;
import com.strongmemoryapi.dto.response.WordResponse;
import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.domain.entity.word.WordEntity;
import com.strongmemoryapi.repository.word.WordRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private DifficultyService difficultyService;

    @CacheEvict(value = "wordIdsByDifficulty", key = "#difficultyId")
    public WordResponse register(WordRegistrationRequest request){
        if(wordRepository.existsByWord(request.word())){
            throw new ResourceAlreadyExistsException("Palavra já cadastrada.");
        }

        DifficultyEntity difficulty = difficultyService.getByDifficultyName(request.difficulty());
        Byte difficultyId = difficulty.getId();

        WordEntity word = new WordEntity();
        word.setWord(request.word());
        word.setDifficulty(difficulty);

        return this.parseToWordResponse(wordRepository.save(word));
    }

    @CacheEvict(value = "wordIdsByDifficulty", key = "#difficultyId")
    public void update(Long id, WordUpdateRequest request){
        WordEntity word = wordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palavra não encontrada."));

        Byte difficultyId = word.getDifficulty().getId();

        word.setWord(request.word());

        wordRepository.save(word);
    }

    @CacheEvict(value = "wordIdsByDifficulty", key = "#difficultyId")
    public void delete(Long id){
        Byte difficultyId = wordRepository.findDifficultyIdById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Palavra não encontrada."));
        wordRepository.deleteById(id);
    }

    public List<WordResponse> getRandomWords(String difficulty, int quantityWords){
        if(quantityWords < 2){
            throw new IllegalArgumentException("Quantidade de palavras inválida para sorteio.");
        }

        DifficultyEntity currentDifficulty = difficultyService.getByDifficultyName(difficulty);

        Long totalWords = wordRepository.countByDifficulty(currentDifficulty);
        if(totalWords < quantityWords){
            throw new InsufficientWordsException();
        }

        List<Long> randomIds = new ArrayList<>();
        List<Long> wordsIds = findIdsByDifficulty(currentDifficulty);

        Random r = new Random();
        for(int i = 0; i < quantityWords; i++){
            int randomPos = r.nextInt(0, wordsIds.size() - 1);
            Long randomId = wordsIds.remove(randomPos);
            randomIds.add(randomId);
        }

        return wordRepository
                .findByIdIn(randomIds)
                .stream()
                .map(this::parseToWordResponse)
                .toList();
    }

    public Page<WordResponse> getAllByDifficulty(
            String difficulty,
            int page,
            int size,
            String sortBy
    ){
        DifficultyEntity currentDifficulty = difficultyService.getByDifficultyName(difficulty);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return wordRepository
                    .findAllByDifficulty(pageable, currentDifficulty)
                    .map(this::parseToWordResponse);
    }

    @Cacheable(value = "wordIdsByDifficulty", key = "#difficultyId")
    public List<Long> findIdsByDifficulty(DifficultyEntity difficulty) {
        Byte difficultyId = difficulty.getId();
        return wordRepository.findIdsByDifficulty(difficulty);
    }

    private WordResponse parseToWordResponse(WordEntity word){
        return new WordResponse(
                word.getId(),
                word.getWord()
        );
    }

}
