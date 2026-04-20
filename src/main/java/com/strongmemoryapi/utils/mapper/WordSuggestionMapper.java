package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.entity.wordsuggestion.WordSuggestionEntity;
import com.strongmemoryapi.dto.response.WordSuggestionResponse;

public class WordSuggestionMapper {

    public static  WordSuggestionResponse toDTO(WordSuggestionEntity suggestion){
        return new WordSuggestionResponse(
                suggestion.getId(),
                suggestion.getSuggestedWord(),
                suggestion.getSuggestedDifficulty(),
                suggestion.getSuggestedAt(),
                suggestion.getUser().getUsername(),
                suggestion.getUser().getEmail()
        );
    }

}
