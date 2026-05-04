package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.WordDrawnModel;
import com.strongmemoryapi.dto.response.WordDrawnResponse;

public class WordDrawnMapper {

    public static WordDrawnResponse toDTO(WordDrawnModel model){
        return new WordDrawnResponse(
                model.getId(),
                model.getWordString(),
                model.getLastTypedWord(),
                model.getOrderIndex(),
                model.getIsCorrect(),
                model.getWasShown()
        );
    }

}
