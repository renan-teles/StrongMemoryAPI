package com.strongmemoryapi.dto.response;

public record ScoreRecordResponse(
        Long id,
        Integer score,
        String difficulty
) {}
