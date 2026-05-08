package com.strongmemoryapi.dto.word.suggestions;

import java.time.Instant;

public record WordSuggestionResponse(
   Long id,
   String word,
   String difficulty,
   Instant suggestedAt
) {}
