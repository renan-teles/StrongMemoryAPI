package com.strongmemoryapi.dto.matchhistory;

import com.strongmemoryapi.domain.model.MatchPlayedModel;

public record FinishMatchDTO(
        Boolean hasNewHighestScore,
        Integer highestScore,
        MatchPlayedModel match
) {}
