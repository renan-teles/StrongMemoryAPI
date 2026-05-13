package com.strongmemoryapi.projection.matchhistory;

import java.time.LocalDate;

public interface ResponseTimeTimelineProjection {
    LocalDate getMatchDate();
    Long getTotalMatches();
    Double getAverageResponseTimeMS();
    Long getFastestResponseTimeMS();
    Long getSlowestResponseTimeMS();
}
