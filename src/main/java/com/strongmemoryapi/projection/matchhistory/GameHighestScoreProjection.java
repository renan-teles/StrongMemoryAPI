package com.strongmemoryapi.projection.matchhistory;

import com.strongmemoryapi.domain.enums.MatchMode;

import java.time.LocalDate;

public interface GameHighestScoreProjection {
    String getDifficulty();
    MatchMode getMatchMode();
    Long getHighestScore();
    LocalDate getMatchDate();
}
