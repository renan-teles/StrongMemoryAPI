package com.strongmemoryapi.dto.user.scorerecord;

import com.strongmemoryapi.domain.model.ScoreRecordModel;

public record UpdateScoreDTO(
        Boolean hasNewHighestScore,
        ScoreRecordModel score
) {}
