package com.strongmemoryapi.dto.matchhistory;

public record HighestScoreDTO(
        Boolean hasNewHighestScore,
        Integer score
) {}
