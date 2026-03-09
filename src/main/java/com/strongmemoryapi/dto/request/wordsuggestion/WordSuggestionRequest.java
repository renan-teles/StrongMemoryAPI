package com.strongmemoryapi.dto.request.wordsuggestion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WordSuggestionRequest(
   @Size(min = 2, message = "Palavra inválida.")
   String suggestedWord,

   @NotBlank(message = "Dificuldade inválida.")
   String suggestedDifficulty
) {}
