package com.strongmemoryapi.dto.word;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterWordRequest(
        @NotBlank(message = "Palavra não pode ser nula ou vazia.")
        @Size(min = 2, message = "Palavra inválida.")
        String word,

        @NotBlank(message = "Dificuldade não pode ser nula ou vazia.")
        String difficulty
) {}
