package com.strongmemoryapi.service.scorerecord;

import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.scorerecord.ScoreRecordRequest;
import com.strongmemoryapi.dto.response.ScoreRecordResponse;
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

    @Autowired
    private ScoreRecordRepository scoreRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DifficultyService difficultyService;

    @Autowired
    private UserRoleService roleService;

    public void registerInitialUserScores(UserEntity user){
        if(!roleService.isPlayerRole(user.getRole())){
            throw new IllegalArgumentException(
                    "Papel de usuário inválido para cadastro de pontuações iniciais."
            );
        }

        userService.checkExitsById(user.getId(), "Jogador não encontrado.");

        List<DifficultyEntity> difficulties =  difficultyService.getAllEntityObjects();
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

    public List<ScoreRecordResponse> getUserScoreRecords(Long userId){
        userService.checkExitsById(userId, "Jogador não encontrado.");

        return scoreRepository
                .findByUserId(userId)
                .stream()
                .map(this::parseToScoreRecordResponse)
                .toList();
    }

    public ScoreRecordResponse getUserScoreRecord(Long userId, String difficultyName){
        userService.checkExitsById(userId, "Jogador não encontrado.");

        DifficultyEntity difficulty = difficultyService.getByDifficultyName(difficultyName);

        ScoreRecordEntity score = scoreRepository.findByUserIdAndDifficulty(userId, difficulty)
                .orElseThrow(() -> new ResourceNotFoundException("Pontuação não encontrada."));

        return parseToScoreRecordResponse(score);
    }

    public ScoreRecordResponse updateScoreRecord(Long id, ScoreRecordRequest request){
        ScoreRecordEntity score = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pontuação não encontrada."));

        if(request.newScore() <= score.getScore()){
            throw new IllegalArgumentException("Nova pontuação inválida.");
        }

        score.setScore(request.newScore());

        return parseToScoreRecordResponse(scoreRepository.save(score));
    }

    private ScoreRecordResponse parseToScoreRecordResponse(ScoreRecordEntity score){
        return new ScoreRecordResponse(
                score.getId(),
                score.getScore(),
                score.getDifficulty().getDifficulty()
        );
    }

}
