package com.strongmemoryapi.dto.response;

public record WordResponse(
        Long id,
        String word,
        String difficulty
) {}
