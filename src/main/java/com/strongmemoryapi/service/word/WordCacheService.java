package com.strongmemoryapi.service.word;

import com.strongmemoryapi.repository.word.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordCacheService {

    @Autowired
    private WordRepository repository;

    @Cacheable(value = "wordIdsByDifficulty", key = "#difficulty")
    public List<Long> findIdsByDifficultyName(String difficulty) {
        System.out.println("ATENÇÃO: INDO BUSCAR IDS DAS PALAVRAS");
        return repository.findIdsByDifficulty_NameAndDeletedFalse(difficulty);
    }

}
