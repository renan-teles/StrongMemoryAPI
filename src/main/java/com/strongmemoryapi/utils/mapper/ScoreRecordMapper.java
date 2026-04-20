package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.entity.scorerecord.ScoreRecordEntity;
import com.strongmemoryapi.dto.response.ScoreRecordResponse;

public class ScoreRecordMapper {

    public static ScoreRecordResponse toDTO(ScoreRecordEntity score) {
        return new ScoreRecordResponse(
                score.getId(),
                score.getScore(),
                score.getDifficulty().getDifficulty()
        );
    }

}
