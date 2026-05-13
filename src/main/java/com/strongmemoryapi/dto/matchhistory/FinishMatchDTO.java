package com.strongmemoryapi.dto.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;

public record FinishMatchDTO(
        Boolean hasNewHighestScore,
        Integer highestScore,
        MatchPlayedModel match
) {}
