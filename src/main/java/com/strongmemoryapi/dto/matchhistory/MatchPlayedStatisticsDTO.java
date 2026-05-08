package com.strongmemoryapi.dto.matchhistory;

import java.math.BigDecimal;

public record MatchPlayedStatisticsDTO(
   int totalWords,
   int numberCorrectAnswers,
   int numberErrors,
   BigDecimal accuracy
) {}
