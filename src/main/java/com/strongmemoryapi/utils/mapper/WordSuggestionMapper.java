package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.word.suggestion.WordSuggestionModel;
import com.strongmemoryapi.dto.word.suggestions.WordSuggestionResponse;

public class WordSuggestionMapper {

    public static  WordSuggestionResponse toDTO(WordSuggestionModel model){
        return new WordSuggestionResponse(
                model.getId(),
                model.getWord(),
                model.getDifficultyName(),
                model.getSuggestedAt()
        );
    }

}
