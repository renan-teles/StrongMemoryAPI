package com.strongmemoryapi.dto.difficulty;

public record DifficultyResponse(
        String name,
        Integer numberWords,
        Integer increaseDisplayTimeSeconds,
        Integer increaseTypingTimeSeconds
) {}
