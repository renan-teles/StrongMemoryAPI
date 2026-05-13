package com.strongmemoryapi.projection.matchhistory;

import java.time.LocalDate;

public interface ScoreTimelineProjection {
    LocalDate getMatchDate();
    String getDifficulty();
    Long getHighestScore();
    Double getAverageScore();
    Long getTotalMatches();
}
