package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.entity.word.WordEntity;
import com.strongmemoryapi.dto.response.WordResponse;

public class WordMapper {

    public static WordResponse toDTO(WordEntity word){
        return new WordResponse(
                word.getId(),
                word.getWord(),
                word.getDifficulty().getDifficulty()
        );
    }

}
