package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.dto.response.DifficultyResponse;

public class DifficultyMapper {

    public static DifficultyResponse toDTO(DifficultyEntity diff) {
        return new DifficultyResponse(
                diff.getId(),
                diff.getDifficulty(),
                diff.getTranslation(),
                diff.getMaxQuantityWords(),
                diff.getIncreaseDisplayTimeSeconds(),
                diff.getIncreaseTypingTimeSeconds()
        );
    }

}
