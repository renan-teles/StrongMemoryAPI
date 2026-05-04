package com.strongmemoryapi.service.matchhistory;

import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.MatchPlayedModel;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.dto.RegisterInitialDataMatchPlayedDTO;
import com.strongmemoryapi.repository.MatchPlayedRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class MatchPlayedService {

    @Autowired
    private MatchPlayedRepository repository;

    @Autowired
    private DifficultyService difficultyService;

    public MatchPlayedModel registerInitialData(
            Long userId,
            DifficultyModel difficulty,
            boolean infiniteMode
    ){
        UserModel user = new UserModel();
        user.setId(userId);

        MatchPlayedModel match = new MatchPlayedModel();
        match.setDifficulty(difficulty);
        match.setUser(user);
        match.setInfiniteMode(infiniteMode);

        try {
            return repository.save(match);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isForeignKeyViolation(ex)){
                throw new ResourceNotFoundException("Usuário não encontrado");
            }
            throw ex;
        }
    }

}
