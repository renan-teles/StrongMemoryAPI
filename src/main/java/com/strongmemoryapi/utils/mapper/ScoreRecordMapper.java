package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.ScoreRecordModel;
import com.strongmemoryapi.dto.user.scorerecord.ScoreRecordResponse;

public class ScoreRecordMapper {

    public static ScoreRecordResponse toDTO(ScoreRecordModel model) {
        return new ScoreRecordResponse(
                model.getId(),
                model.getScore(),
                model.getDifficultyName()
        );
    }

}
