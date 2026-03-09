package com.strongmemoryapi.dto.request.scorerecord;

import jakarta.validation.constraints.Min;

public record ScoreRecordRequest(
   @Min(value = 1, message = "Pontuação inválida.")
   Integer newScore
) {}