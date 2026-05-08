package com.strongmemoryapi.dto.matchhistory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MatchPlayedDTO(

   @NotNull(message = "Id da partida precisa estar definido.")
   Long id,

   @NotBlank(message = "A dificuldade não pode ser nula o vazia.")
   String difficulty,

   @NotNull(message = "O status de finalização por tempo precisa estar definido.")
   Boolean finishedByTimeout,

   @NotNull(message = "O status de completou ou não a sequência precisa estar definido.")
   Boolean completedSequenceWords,

   @NotNull(message = "A média de tempo de resposta precisa estar definida.")
   @Min(value = 0, message = "Tempo médio de resposta inálido.")
   Integer avgResponseTimeMs

) {}
