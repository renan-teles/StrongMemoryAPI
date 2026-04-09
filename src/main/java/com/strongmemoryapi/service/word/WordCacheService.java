package com.strongmemoryapi.service.word;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.repository.word.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordCacheService {

    @Autowired
    private WordRepository repository;

    @Cacheable(value = "wordIdsByDifficulty", key = "#difficulty != null ? #difficulty.id : 0")
    public List<Long> findIdsByDifficulty(DifficultyEntity difficulty) {
        return repository.findIdsByDifficulty(difficulty);
    }

}
