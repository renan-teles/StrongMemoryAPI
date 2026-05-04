package com.strongmemoryapi.dto.response;

import java.util.List;

public record GameResponse(
   Long mathPlayedId,
   List<WordDrawnResponse> randomWords
) {}
