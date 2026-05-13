package com.strongmemoryapi.projection.matchhistory;

import com.strongmemoryapi.domain.enums.MatchMode;

public interface GameModeAnalyticsProjection {
    MatchMode getMatchMode();
    Long getTotalMatches();
    Long getTotalCorrectAnswers();
    Long getTotalErrors();
    Double getOverallAccuracyPercentage();
    Double getAverageAccuracyPercentage();
    Double getAverageDurationMS();
    Double getAverageResponseTimeMS();
}
