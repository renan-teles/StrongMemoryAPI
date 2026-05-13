package com.strongmemoryapi.service.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import com.strongmemoryapi.dto.matchhistory.MatchPlayedStatisticsDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class MatchStatisticsService {

    public MatchPlayedStatisticsDTO calculate(
            MatchPlayedModel match,
            List<DrawnWordDTO> drawnWords
    ){
        if(match.getStoppedPlayingAt() == null){
            throw new IllegalArgumentException(
                    "Momento da finalização da partida não definido."
            );
        }

        int totalWords = drawnWords.size();

        int numberCorrectAnswers = (int) drawnWords
                .stream()
                .filter((w) -> w.isCorrect() && w.wasShown())
                .count();

        int numberErrors = (int) drawnWords
                .stream()
                .filter((w) -> !w.isCorrect() && w.wasShown())
                .count();

        double accuracyPercentage = 0.00;
        double accuracyMultiplication = numberCorrectAnswers * 100.00;
        int totalAnswersWords = (numberErrors + numberCorrectAnswers);

        if(accuracyMultiplication > 0){
            accuracyPercentage = accuracyMultiplication / totalAnswersWords;
        }

        long durationMs = Duration
                .between(match.getStartPlayingAt(), match.getStoppedPlayingAt())
                .toMillis();

        return new MatchPlayedStatisticsDTO(
                totalWords,
                numberCorrectAnswers,
                numberErrors,
                BigDecimal.valueOf(accuracyPercentage),
                durationMs
        );
    }

}
