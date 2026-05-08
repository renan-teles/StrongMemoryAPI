package com.strongmemoryapi.dto.matchhistory;

import com.strongmemoryapi.dto.word.WordDTO;
import jakarta.validation.constraints.NotNull;

public record DrawnWordDTO(

        @NotNull(message = "Id da palavra sorteada precisa estar definido.")
        Long id,

        @NotNull(message = "Os dados da palavra sorteada precisam estar definidos.")
        WordDTO word,

        @NotNull(message = "A posição da palavra no sorteio precisa estar definida.")
        Integer orderIndex,

        @NotNull(message = "O status de acerto ou erro precisa estar definido.")
        Boolean isCorrect,

        @NotNull(message = "O status de foi visualizada ou não precisa estar definido.")
        Boolean wasShown,

        String lastTypedWord

) {}
