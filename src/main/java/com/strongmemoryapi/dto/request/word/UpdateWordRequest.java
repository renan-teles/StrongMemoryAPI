package com.strongmemoryapi.dto.request.word;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateWordRequest(
        @NotBlank(message = "Palavra não pode ser nula ou vazia.")
        @Size(min = 2, message = "Palavra inválida.")
        String word
) {}
