package com.strongmemoryapi.dto.game;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StartGameRequest (

  @NotBlank(message = "Dificuldade não pode ser nula ou vazia.")
  String difficulty,

  @NotNull(message = "Modo de jogo precisa estar definido.")
  Boolean infiniteMode

){}
