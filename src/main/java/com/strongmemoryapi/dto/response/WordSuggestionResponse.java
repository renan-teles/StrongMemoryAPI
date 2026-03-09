package com.strongmemoryapi.dto.response;

import java.time.Instant;

public record WordSuggestionResponse(
   Long id,
   String suggestedWord,
   String suggestedDifficulty,
   Instant suggestedAt,
   String username,
   String userEmail
) {}
