package com.strongmemoryapi.dto.response;

public record DifficultyResponse(
        Byte id,
        String difficulty,
        String translation,
        Byte maxQuantityWords,
        Byte increaseDisplayTimeSeconds,
        Byte increaseTypingTimeSeconds
) {}
