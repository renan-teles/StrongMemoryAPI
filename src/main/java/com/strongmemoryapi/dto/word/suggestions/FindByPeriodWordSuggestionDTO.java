package com.strongmemoryapi.dto.word.suggestions;

import java.time.LocalDate;
import java.util.Optional;

public record FindByPeriodWordSuggestionDTO(
    LocalDate startDate,
    LocalDate endDate,
    Optional<String> difficulty
){

    public static FindByPeriodWordSuggestionDTO of(LocalDate startDate, LocalDate endDate){
        return new FindByPeriodWordSuggestionDTO(startDate, endDate, Optional.empty());
    }

    public static FindByPeriodWordSuggestionDTO of(
            LocalDate startDate,
            LocalDate endDate,
            String difficulty
    ){
        return new FindByPeriodWordSuggestionDTO(startDate, endDate, Optional.ofNullable(difficulty));
    }

}
