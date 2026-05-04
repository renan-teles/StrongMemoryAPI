package com.strongmemoryapi.service.difficulty;

import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.repository.DifficultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifficultyService {

    private final String NOT_FOUND_MESSAGE = "Dificuldade não encontrada.";

    @Autowired
    private DifficultyRepository repository;

    public List<DifficultyModel> findAll(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public DifficultyModel findByName(String name){
        return repository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }

}
