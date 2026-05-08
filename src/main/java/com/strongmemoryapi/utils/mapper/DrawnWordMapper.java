package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.DrawnWordModel;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import com.strongmemoryapi.dto.word.WordDTO;

public class DrawnWordMapper {

    public static DrawnWordDTO toDTO(DrawnWordModel model){
        return new DrawnWordDTO(
                model.getId(),
                new WordDTO(model.getWordId(), model.getWordString()),
                model.getOrderIndex(),
                model.getIsCorrect(),
                model.getWasShown(),
                model.getLastTypedWord()
        );
    }

}