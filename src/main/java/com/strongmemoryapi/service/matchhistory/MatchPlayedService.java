package com.strongmemoryapi.service.matchhistory;

import com.strongmemoryapi.domain.exception.local.BusinessRuleException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.MatchPlayedModel;
import com.strongmemoryapi.domain.model.ScoreRecordModel;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.dto.matchhistory.FinishMatchDTO;
import com.strongmemoryapi.dto.matchhistory.MatchPlayedDTO;
import com.strongmemoryapi.dto.matchhistory.MatchPlayedStatisticsDTO;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import com.strongmemoryapi.dto.user.scorerecord.UpdateScoreDTO;
import com.strongmemoryapi.repository.MatchPlayedRepository;
import com.strongmemoryapi.service.user.scorerecord.ScoreRecordService;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class MatchPlayedService {

    @Autowired
    private MatchPlayedRepository repository;

    @Autowired
    private ScoreRecordService scoreService;

    public MatchPlayedModel register(
            Long userId,
            DifficultyModel difficulty,
            boolean infiniteMode
    ){
        UserModel user = new UserModel();
        user.setId(userId);

        MatchPlayedModel match =
                createInitialModel(difficulty, user, infiniteMode);

        try {
            return repository.save(match);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isForeignKeyViolation(ex)){
                throw new ResourceNotFoundException("Usuário não encontrado");
            }
            throw ex;
        }
    }

    @Transactional
    public FinishMatchDTO finish(
            Long userId,
            List<DrawnWordDTO> drawnWords,
            MatchPlayedModel matchModel,
            MatchPlayedDTO matchDTO
    ){
        boolean completedSequenceWords = matchDTO.completedSequenceWords();

        if(matchDTO.finishedByTimeout() == completedSequenceWords){
            throw new BusinessRuleException(
                 "Status de finalização da partida inválidos."
            );
        }

        long wasShownCount = drawnWords
                .stream()
                .filter(DrawnWordDTO::wasShown)
                .count();

        if(completedSequenceWords && (wasShownCount != drawnWords.size())){
            throw new BusinessRuleException(
                 "Status de sequência completa inválido."
            );
        }

        DifficultyModel difficulty = matchModel.getDifficulty();

        MatchPlayedStatisticsDTO statistics = calculateStatistics(drawnWords);
        setFinishedStatistics(
                matchModel,
                statistics,
                difficulty
        );

        matchModel.setFinishedByTimeout(matchDTO.finishedByTimeout());
        matchModel.setAvgResponseTimeMs(matchDTO.avgResponseTimeMs());

        matchModel.setGaveUp(false);

        if(matchModel.getInfiniteMode()){
            matchModel.setCompletedSequenceWords(false);
        } else {
            matchModel.setCompletedSequenceWords(matchDTO.completedSequenceWords());
        }

        matchModel.setStoppedPlayingAt(Instant.now());
        matchModel = repository.save(matchModel);

        UpdateScoreDTO scoreDTO =
                updateScore(
                        userId,
                        difficulty.getName(),
                        matchModel.getScoreAchieved(),
                        matchModel.getInfiniteMode()
                );

        return new FinishMatchDTO(
                scoreDTO.hasNewHighestScore(),
                scoreDTO.score().getScore(),
                matchModel
        );
    }

    @Transactional
    public void gaveUp(
            MatchPlayedDTO matchDTO,
            MatchPlayedModel matchModel,
            List<DrawnWordDTO> drawnWords
    ){
        DifficultyModel difficulty = matchModel.getDifficulty();

        MatchPlayedStatisticsDTO statistics = calculateStatistics(drawnWords);
        setFinishedStatistics(
                matchModel,
                statistics,
                difficulty
        );

        matchModel.setAvgResponseTimeMs(matchDTO.avgResponseTimeMs());
        matchModel.setGaveUp(true);
        matchModel.setFinishedByTimeout(false);
        matchModel.setCompletedSequenceWords(false);
        matchModel.setStoppedPlayingAt(Instant.now());

        repository.save(matchModel);
    }

    public MatchPlayedModel findByIdAndUserIdExcludeGaveUpsAndFinished(
            Long id,
            Long userId
    ) {
        return repository
                .findByIdAndUser_IdAndGaveUpFalseAndStoppedPlayingAtNull(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada"));
    }

    private MatchPlayedModel createInitialModel(
            DifficultyModel difficulty,
            UserModel user,
            boolean infiniteMode
    ){
        MatchPlayedModel match = new MatchPlayedModel();
        match.setDifficulty(difficulty);
        match.setUser(user);

        match.setInfiniteMode(infiniteMode);

        match.setCompletedSequenceWords(false);
        match.setGaveUp(false);
        match.setFinishedByTimeout(false);

        match.setNumberErrors(0);
        match.setNumberCorrectAnswers(0);
        match.setTotalWords(0);
        match.setScoreAchieved(0);
        match.setAccuracy(BigDecimal.ZERO);
        match.setAvgResponseTimeMs(0);

        return match;
    }

    private MatchPlayedStatisticsDTO calculateStatistics(
            List<DrawnWordDTO> drawnWords
    ){
        int totalWords = drawnWords.size();

        int numberCorrectAnswers = (int) drawnWords
                .stream()
                .filter((w) -> w.isCorrect() && w.wasShown())
                .count();

        int numberErrors = (int) drawnWords
                .stream()
                .filter((w) -> !w.isCorrect() && w.wasShown())
                .count();

        double accuracy = 0;
        double accuracyMultiplication = numberCorrectAnswers * 100.00;

        if(accuracyMultiplication > 0){
            accuracy = accuracyMultiplication / totalWords;
        }

        return new MatchPlayedStatisticsDTO(
                totalWords,
                numberCorrectAnswers,
                numberErrors,
                BigDecimal.valueOf(accuracy)
        );
    }

    private void setFinishedStatistics(
            MatchPlayedModel match,
            MatchPlayedStatisticsDTO statistics,
            DifficultyModel difficulty
    ){
        int numberCorrectAnswers = statistics.numberCorrectAnswers();
        match.setNumberCorrectAnswers(numberCorrectAnswers);

        match.setTotalWords(statistics.totalWords());
        match.setNumberErrors(statistics.numberErrors());
        match.setAccuracy(statistics.accuracy());

        int increasePerHit = difficulty.getIncreasePerHit();
        int scoreAchieved = increasePerHit * numberCorrectAnswers;
        match.setScoreAchieved(scoreAchieved);
    }

    private UpdateScoreDTO updateScore(
            Long userId,
            String difficultyName,
            Integer scoreAchieved,
            boolean infiniteMode
    ){
        ScoreRecordModel currentScore = scoreService
                .findUserScoreRecord(userId, difficultyName, infiniteMode);

        boolean hasNewHighestScore = scoreAchieved > currentScore.getScore();
        if(hasNewHighestScore){
            currentScore.setScore(scoreAchieved);
            currentScore = scoreService.saveScoreRecord(currentScore);
        }

        return new UpdateScoreDTO(hasNewHighestScore, currentScore);
    }

}

