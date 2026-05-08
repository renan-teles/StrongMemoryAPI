package com.strongmemoryapi.dto.game;

public record FinishGameResponse(
      Boolean hasNewHighestScore,
      Integer highestScore
) {}
