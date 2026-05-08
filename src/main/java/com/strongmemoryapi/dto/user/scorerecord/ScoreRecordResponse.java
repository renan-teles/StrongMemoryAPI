package com.strongmemoryapi.dto.user.scorerecord;

public record ScoreRecordResponse(
        Long id,
        Integer score,
        String difficulty
) {}
