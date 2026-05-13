package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.dto.difficulty.DifficultyResponse;

public class DifficultyMapper {

    public static DifficultyResponse toDTO(DifficultyModel model) {
        return new DifficultyResponse(
                model.getName(),
                model.getNumberWords(),
                model.getIncreaseDisplayTimeSeconds(),
                model.getIncreaseTypingTimeSeconds()
        );
    }

}
