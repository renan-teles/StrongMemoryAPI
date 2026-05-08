package com.strongmemoryapi.service.user.scorerecord;

import com.strongmemoryapi.domain.exception.local.BusinessRuleException;
import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.ScoreRecordModel;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.repository.ScoreRecordRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreRecordService {

    private final String NOT_FOUND_MESSAGE = "Pontuação não encontrada.";

    @Autowired
    private ScoreRecordRepository repository;

    @Autowired
    private DifficultyService difficultyService;

    @Transactional
    public void createInitialUserScores(UserModel user){
        if(!user.isPlayer()){
            throw new BusinessRuleException(
                 "Papel de usuário inválido para cadastro de pontuações iniciais."
            );
        }

        List<DifficultyModel> difficulties = difficultyService.findAll();
        if(difficulties.isEmpty()){
            throw new ResourceNotFoundException(
                 "Dificuldades não foram encontradas para registro e associação de pontuações iniciais."
            );
        }

        List<ScoreRecordModel> initialScores = new ArrayList<>();

        for(DifficultyModel difficulty : difficulties){
            for(int i = 0; i <= 1; i++){
                ScoreRecordModel score = new ScoreRecordModel();
                score.setDifficulty(difficulty);
                score.setUser(user);
                score.setScore(0);
                score.setInfiniteMode(i == 0);

                initialScores.add(score);
            }
        }

        try {
            repository.saveAll(initialScores);
        } catch(DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isForeignKeyViolation(ex)){
                throw new ResourceNotFoundException("Usuário não encontrado.");
            }
            if(DatabaseErrorUtils.isUniqueConstraintViolation(ex)){
                throw new ResourceAlreadyExistsException("Pontuações já existentes para usuário.");
            }
            throw ex;
        }
    }

    public ScoreRecordModel saveScoreRecord(ScoreRecordModel newScore){
        try {
            return repository.save(newScore);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isUniqueConstraintViolation(ex)){
                throw new ResourceAlreadyExistsException("Pontuação já existente para usuário.");
            }
            throw ex;
        }
    }

    public ScoreRecordModel findUserScoreRecord(
            Long userId,
            String difficultyName,
            boolean infiniteMode
    ){
        return repository
                .findByUser_IdAndDifficulty_NameAndInfiniteMode(
                        userId,
                        difficultyName.toLowerCase(),
                        infiniteMode
                )
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }

    /*
    public List<ScoreRecordModel> findUserScoreRecords(Long userId){
        return repository.findByUser_Id(userId);
    }

    public ScoreRecordModel updateScoreRecord(
            Long id,
            Long userId,
            ScoreRecordRequest request
    ){
        ScoreRecordModel score = repository
                .findByIdAndUser_Id(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));

        if(request.newScore() <= score.getScore()){
            throw new IllegalArgumentException("Nova pontuação inválida.");
        }
        score.setScore(request.newScore());

        return repository.save(score);
    }
    */

}
