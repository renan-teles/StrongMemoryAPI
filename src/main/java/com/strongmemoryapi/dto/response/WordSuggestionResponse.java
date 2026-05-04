package com.strongmemoryapi.dto.response;

import java.time.Instant;

public record WordSuggestionResponse(
   Long id,
   String word,
   String difficulty,
   Instant suggestedAt
) {}
