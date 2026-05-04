package com.strongmemoryapi.dto.response;

public record DifficultyResponse(
        String name,
        Integer quantityWords,
        Integer increaseDisplayTimeSeconds,
        Integer increaseTypingTimeSeconds
) {}
