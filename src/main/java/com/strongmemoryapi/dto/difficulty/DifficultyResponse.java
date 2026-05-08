package com.strongmemoryapi.dto.difficulty;

public record DifficultyResponse(
        String name,
        Integer quantityWords,
        Integer increaseDisplayTimeSeconds,
        Integer increaseTypingTimeSeconds
) {}
