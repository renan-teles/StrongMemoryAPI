package com.strongmemoryapi.service.matchhistory;

import com.strongmemoryapi.domain.enums.MatchMode;
import com.strongmemoryapi.domain.enums.MatchResult;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.dto.matchhistory.*;
import com.strongmemoryapi.repository.matchhistory.MatchPlayedRepository;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MatchPlayedService {

    @Autowired
    private MatchPlayedRepository repository;

    @Autowired
    private MatchStatisticsService statisticsService;

    public MatchPlayedModel register(
            Long userId,
            DifficultyModel difficulty,
            MatchMode mode
    ){
        UserModel user = new UserModel();
        user.setId(userId);

        MatchPlayedModel match =
                createInitialModel(difficulty, user, mode);

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
        DifficultyModel difficulty = matchModel.getDifficulty();

        matchModel.setStoppedPlayingAt(Instant.now());
        matchModel.setAverageResponseTimeMs(matchDTO.averageResponseTimeMs());

        MatchPlayedStatisticsDTO statistics =
                statisticsService.calculate(matchModel, drawnWords);

        setStatistics(matchModel, statistics, difficulty);

        matchModel.setDurationMs(statistics.matchDurationMs());

        if(matchModel.isInFiniteMode()){
            setMatchResult(matchModel, matchDTO, drawnWords);
        }

        HighestScoreDTO scoreDTO = checkHasNewHighestScore(matchModel, userId);

        MatchPlayedModel finishedMatch = repository.save(matchModel);

        return new FinishMatchDTO(
            scoreDTO.hasNewHighestScore(),
            scoreDTO.score(),
            finishedMatch
        );
    }

    public void gaveUp(
            MatchPlayedDTO matchDTO,
            MatchPlayedModel matchModel,
            List<DrawnWordDTO> drawnWords
    ){
        DifficultyModel difficulty = matchModel.getDifficulty();

        matchModel.setStoppedPlayingAt(Instant.now());

        MatchPlayedStatisticsDTO statistics =
                statisticsService.calculate(matchModel, drawnWords);

        setStatistics(matchModel, statistics, difficulty);

        matchModel.setDurationMs(statistics.matchDurationMs());
        matchModel.setAverageResponseTimeMs(matchDTO.averageResponseTimeMs());
        matchModel.setResult(MatchResult.GAVE_UP);

        repository.save(matchModel);
    }

    public MatchPlayedModel findNotCompletedByIdAndUserId(Long id, Long userId) {
        return repository
                .findByIdAndUser_IdAndResultAndStoppedPlayingAtNull(
                     id, userId, MatchResult.NOT_COMPLETED
                )
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada"));
    }

    private MatchPlayedModel createInitialModel(
            DifficultyModel difficulty,
            UserModel user,
            MatchMode mode
    ){
        MatchPlayedModel match = new MatchPlayedModel();
        match.setDifficulty(difficulty);
        match.setUser(user);

        match.setMode(mode);
        match.setResult(MatchResult.NOT_COMPLETED);
        match.setAccuracyPercentage(BigDecimal.ZERO);
        match.setDurationMs(0L);
        match.setNumberErrors(0);
        match.setNumberCorrectAnswers(0);
        match.setTotalWords(0);
        match.setScoreAchieved(0);
        match.setAverageResponseTimeMs(0);

        return match;
    }

    private void setStatistics(
            MatchPlayedModel match,
            MatchPlayedStatisticsDTO statistics,
            DifficultyModel difficulty
    ){
        int numberCorrectAnswers = statistics.numberCorrectAnswers();
        match.setNumberCorrectAnswers(numberCorrectAnswers);

        match.setTotalWords(statistics.totalWords());
        match.setNumberErrors(statistics.numberErrors());
        match.setAccuracyPercentage(statistics.accuracyPercentage());

        int increasePerHit = difficulty.getIncreasePerHit();
        int scoreAchieved = increasePerHit * numberCorrectAnswers;
        match.setScoreAchieved(scoreAchieved);
    }

    private void setMatchResult(
            MatchPlayedModel match,
            MatchPlayedDTO matchDTO,
            List<DrawnWordDTO> drawnWords
    ){
        final MatchResult result = matchDTO.result();
        final MatchResult completedResult = MatchResult.COMPLETED;

        if(!(result.equals(completedResult) || result.equals(MatchResult.TIMEOUT))){
            throw new IllegalArgumentException(
                "Resultado inválido para finalização de partida."
            );
        }

        long wasShownCount = drawnWords
                .stream()
                .filter(DrawnWordDTO::wasShown)
                .count();

        if(
             result.equals(completedResult)
             && (wasShownCount != drawnWords.size())
        ){
            throw new IllegalArgumentException(
                    "Resultado da partida inválido."
            );
        }

        match.setResult(result);
    }

    private HighestScoreDTO checkHasNewHighestScore(MatchPlayedModel match, Long userId){
        Optional<Integer> maxScoreOpt = repository
                .findMaxScoreByUser_IdAndModeAnDifficulty_Name(
                        userId,
                        match.getMode(),
                        match.getDifficulty().getName()
                );

        boolean hasNewHighestScore = true;
        int scoreAchieved = match.getScoreAchieved();

        if(maxScoreOpt.isPresent()) {
            hasNewHighestScore = scoreAchieved > maxScoreOpt.get();
        }

        return new HighestScoreDTO(hasNewHighestScore, scoreAchieved);
    }

}

