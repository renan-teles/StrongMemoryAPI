package com.strongmemoryapi.service.scorerecord;

import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.scorerecord.ScoreRecordRequest;
import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.domain.entity.scorerecord.ScoreRecordEntity;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.repository.scorerecord.ScoreRecordRepository;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.service.user.UserService;
import com.strongmemoryapi.service.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreRecordService {

    private final String
            PLAYER_NOT_FOUND_MSG = "Jogador não encontrado.",
            SCORE_NOT_FOUND_MSG = "Pontuação não encontrada.";

    @Autowired
    private ScoreRecordRepository scoreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DifficultyService difficultyService;

    @Autowired
    private UserRoleService roleService;

    public void createInitialUserScores(UserEntity user){
        if(!roleService.isPlayerRole(user.getRole())){
            throw new IllegalArgumentException(
                    "Papel de usuário inválido para cadastro de pontuações iniciais."
            );
        }

        userService.checkExitsById(user.getId(), "Jogador não encontrado.");

        List<DifficultyEntity> difficulties =  difficultyService.findAllEntityObjects();
        if(difficulties.isEmpty()){
            throw new ResourceNotFoundException(
                    "Dificuldades não foram encontradas para registro e associação de pontuações iniciais."
            );
        }

        List<ScoreRecordEntity> scores = new ArrayList<>();

        for(DifficultyEntity difficulty : difficulties){
            ScoreRecordEntity score = new ScoreRecordEntity();
            score.setDifficulty(difficulty);
            score.setUser(user);
            score.setScore(0);

            scores.add(score);
        }

        scoreRepository.saveAll(scores);
    }

    public List<ScoreRecordEntity> findUserScoreRecords(Long userId){
        userService.checkExitsById(userId, PLAYER_NOT_FOUND_MSG);
        return scoreRepository.findByUserId(userId);
    }

    public ScoreRecordEntity findUserScoreRecord(Long userId, String difficultyName){
        userService.checkExitsById(userId, PLAYER_NOT_FOUND_MSG);

        DifficultyEntity difficulty = difficultyService.findByDifficultyName(difficultyName);

        return scoreRepository.findByUserIdAndDifficulty(userId, difficulty)
                .orElseThrow(() -> new ResourceNotFoundException(SCORE_NOT_FOUND_MSG));
    }

    public ScoreRecordEntity updateScoreRecord(
            Long id,
            Long userId,
            ScoreRecordRequest request
    ){
        ScoreRecordEntity score = scoreRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(SCORE_NOT_FOUND_MSG));

        if(request.newScore() <= score.getScore()){
            throw new IllegalArgumentException("Nova pontuação inválida.");
        }

        score.setScore(request.newScore());
        return scoreRepository.save(score);
    }

}
