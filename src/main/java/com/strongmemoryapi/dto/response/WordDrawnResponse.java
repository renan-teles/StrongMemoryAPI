package com.strongmemoryapi.dto.response;

public record WordDrawnResponse(
   Long id,
   String word,
   String lastTypedWord,
   Integer orderIndex,
   Boolean isCorrect,
   Boolean wasShown
) {}
