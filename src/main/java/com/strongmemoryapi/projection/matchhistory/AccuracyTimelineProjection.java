package com.strongmemoryapi.projection.matchhistory;

import com.strongmemoryapi.domain.enums.MatchMode;

import java.time.LocalDate;

public interface AccuracyTimelineProjection {
    LocalDate getMatchDate();
    MatchMode getMatchMode();
    Long getTotalMatches();
    Long getTotalCorrectAnswers();
    Long getTotalErrors();
    Double getOverallAccuracyPercentage();
    Double getAverageAccuracyPercentage();
}
