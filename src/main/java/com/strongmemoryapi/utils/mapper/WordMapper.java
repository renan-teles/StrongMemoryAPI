package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.WordModel;
import com.strongmemoryapi.dto.response.WordResponse;

public class WordMapper {

    public static WordResponse toDTO(WordModel word){
        return new WordResponse(
               word.getWord(),
               word.getDifficultyName()
        );
    }

}
