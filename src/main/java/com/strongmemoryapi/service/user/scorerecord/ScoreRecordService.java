package com.strongmemoryapi.service.user.scorerecord;

import com.strongmemoryapi.domain.exception.local.BusinessRuleException;
import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.scorerecord.ScoreRecordRequest;
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

        List<ScoreRecordModel> initialScores = difficulties
                .stream()
                .map((difficulty) -> {
                    ScoreRecordModel score = new ScoreRecordModel();
                    score.setDifficulty(difficulty);
                    score.setUser(user);
                    score.setScore(0);

                    return score;
                })
                .toList();

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

    public List<ScoreRecordModel> findUserScoreRecords(Long userId){
        return repository.findByUser_Id(userId);
    }

    public ScoreRecordModel findUserScoreRecord(Long userId, String difficultyName){
        return repository
                .findByUser_IdAndDifficulty_Name(userId, difficultyName.toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
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

}
