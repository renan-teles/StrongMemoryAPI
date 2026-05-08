package com.strongmemoryapi.dto.user.scorerecord;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ScoreRecordRequest(
   @NotNull(message = "Nova pontuação não pode ser nula ou vazia.")
   @Min(value = 1, message = "Pontuação inválida.")
   Integer newScore
) {}