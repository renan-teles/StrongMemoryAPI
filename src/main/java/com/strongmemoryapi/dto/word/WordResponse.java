package com.strongmemoryapi.dto.word;

public record WordResponse(
        Long id,
        String word,
        String difficulty
) {}
