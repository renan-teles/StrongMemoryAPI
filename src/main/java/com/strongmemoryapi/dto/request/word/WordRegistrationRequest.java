package com.strongmemoryapi.dto.request.word;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WordRegistrationRequest(
        @Size(min = 2, message = "Palavra inválida.")
        String word,

        @NotBlank(message = "Dificuldade inválida.")
        String difficulty
) {}
