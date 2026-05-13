package com.strongmemoryapi.projection.matchhistory;

import java.time.LocalDate;

public interface MatchDurationProjection {
    LocalDate getMatchDate();
    Double getAverageDurationMS();
    Long getShortestMatchDurationMS();
    Long getLongestMatchDurationMS();
    Long getTotalMatches();
}
