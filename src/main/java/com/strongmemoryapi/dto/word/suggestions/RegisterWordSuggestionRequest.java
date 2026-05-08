package com.strongmemoryapi.dto.word.suggestions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterWordSuggestionRequest(
   @NotBlank(message = "Sugestão de palavra não pode ser nulo ou vazia.")
   @Size(min = 2, message = "Sugestão de palavra inválida.")
   String word,

   @NotBlank(message = "Dificuldade não pode ser nula ou vazia.")
   String difficulty
) {}
