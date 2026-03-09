package com.strongmemoryapi.dto.request.word;

import jakarta.validation.constraints.Size;

public record WordUpdateRequest(
        @Size(min = 2, message = "Palavra inválida.")
        String word
) {}
