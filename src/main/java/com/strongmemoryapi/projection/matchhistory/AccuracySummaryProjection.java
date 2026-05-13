package com.strongmemoryapi.projection.matchhistory;

public interface AccuracySummaryProjection {
    Long getTotalCorrectAnswers();
    Long getTotalErrors();
    Long getTotalAnswers();
    Double getOverallAccuracyPercentage();
    Double getOverallErrorPercentage();
    Double getAverageAccuracyPercentage();
    Double getAverageErrorPercentage();
}