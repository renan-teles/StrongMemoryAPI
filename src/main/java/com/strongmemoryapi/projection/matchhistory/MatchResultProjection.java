package com.strongmemoryapi.projection.matchhistory;

import com.strongmemoryapi.domain.enums.MatchResult;

public interface MatchResultProjection {
    MatchResult getMatchResult();
    Long getTotalMatches();
    Double getPercentage();
}
