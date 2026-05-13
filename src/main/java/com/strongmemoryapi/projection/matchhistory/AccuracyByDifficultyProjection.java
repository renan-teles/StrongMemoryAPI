package com.strongmemoryapi.projection.matchhistory;

public interface AccuracyByDifficultyProjection {
    String getDifficulty();
    Long getTotalMatches();
    Long getTotalCorrectAnswers();
    Long getTotalErrors();
    Double getOverallAccuracyPercentage();
    Double getAverageAccuracyPercentage();
}