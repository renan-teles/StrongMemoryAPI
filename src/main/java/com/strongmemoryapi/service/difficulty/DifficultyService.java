package com.strongmemoryapi.service.difficulty;

import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.repository.difficulty.DifficultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifficultyService {

    private final String DIFF_NOT_FOUND_MSG = "Dificuldade não encontrada.";

    @Autowired
    private DifficultyRepository repository;

    public List<DifficultyEntity> findAll(){
        return repository.findAll(Sort.by("id"));
    }

    public List<DifficultyEntity> findAllEntityObjects(){
        return repository.findAll();
    }

    public DifficultyEntity findById(Byte id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(DIFF_NOT_FOUND_MSG));
    }

    public DifficultyEntity findByDifficultyName(String difficultyName){
        return repository.findByDifficulty(difficultyName)
                .orElseThrow(() -> new ResourceNotFoundException(DIFF_NOT_FOUND_MSG));
    }

    public void checkExistsByDifficulty(String suggestion){
        if(repository.existsByDifficulty(suggestion)) return;
        throw new ResourceNotFoundException(DIFF_NOT_FOUND_MSG);
    }

}
