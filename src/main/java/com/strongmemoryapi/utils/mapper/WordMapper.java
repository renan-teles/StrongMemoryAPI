package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.word.WordModel;
import com.strongmemoryapi.dto.word.WordResponse;

public class WordMapper {

    public static WordResponse toDTO(WordModel word){
        return new WordResponse(
               word.getId(),
               word.getWord(),
               word.getDifficultyName()
        );
    }

}
