package com.strongmemoryapi.projection.matchhistory;

public interface OverviewAnalyticsProjection {
    Long getTotalMatches();
    Double getAverageCorrectAnswersByMatch();
    Double getAverageErrorsByMatch();
    Long getTotalCorrectAnswers();
    Long getTotalErrors();
    Long getTotalAnswers();
    Double getOverallAccuracyPercentage();
    Double getAverageAccuracyPercentage();
    Double getAverageResponseTimeMS();
    Double getAverageMatchDurationMS();
    Double getGiveUpPercentage();
    Long getTotalTimeoutMatches();
    Long getTotalCompletedMatches();
}
