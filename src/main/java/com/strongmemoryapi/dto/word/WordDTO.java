package com.strongmemoryapi.dto.word;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WordDTO (
        @NotNull(message = "O id da palavra presisa estar definido.")
        Long id,

        @NotBlank(message = "A palavra não pode ser nula ou vazia.")
        String word
){}
