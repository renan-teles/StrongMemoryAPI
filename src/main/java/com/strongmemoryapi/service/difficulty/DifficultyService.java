package com.strongmemoryapi.service.difficulty;

import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.response.DifficultyResponse;
import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.repository.difficulty.DifficultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifficultyService {

    @Autowired
    private DifficultyRepository repository;

    public List<DifficultyResponse> getAll(){
        return repository
                .findAll(Sort.by("id"))
                .stream()
                .map(this::parseToDifficultyResponse)
                .toList();
    }

    public List<DifficultyEntity> getAllEntityObjects(){
        return repository.findAll();
    }

    public DifficultyResponse getById(Byte id){
        DifficultyEntity difficulty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dificuldade não encontrada."));

        return parseToDifficultyResponse(difficulty);
    }

    public DifficultyEntity getByDifficultyName(String difficultyName){
        return repository.findByDifficulty(difficultyName)
                .orElseThrow(() -> new ResourceNotFoundException("Dificuldade não encontrada."));
    }

    private DifficultyResponse parseToDifficultyResponse(DifficultyEntity difficulty){
        return new DifficultyResponse(
                difficulty.getId(),
                difficulty.getDifficulty(),
                difficulty.getTranslation(),
                difficulty.getMaxQuantityWords(),
                difficulty.getIncreaseDisplayTimeSeconds(),
                difficulty.getIncreaseTypingTimeSeconds()
        );
    }
}
