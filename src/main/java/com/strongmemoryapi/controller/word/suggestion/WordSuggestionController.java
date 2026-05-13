package com.strongmemoryapi.controller.word.suggestion;

import com.strongmemoryapi.dto.word.suggestions.FindByPeriodWordSuggestionDTO;
import com.strongmemoryapi.dto.PaginationDTO;
import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.dto.word.suggestions.RegisterWordSuggestionRequest;
import com.strongmemoryapi.dto.word.suggestions.WordSuggestionResponse;
import com.strongmemoryapi.service.word.suggestion.WordSuggestionService;
import com.strongmemoryapi.utils.mapper.WordSuggestionMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import com.strongmemoryapi.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(
      value = "/api/word-suggestion",
      produces = "application/json;charset=UTF-8"
)
public class WordSuggestionController {

    @Autowired
    private WordSuggestionService service;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<WordSuggestionResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "suggestedAt") String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) String difficulty
    ){
        Page<WordSuggestionResponse> suggestions = service
                .findAll(
                        PaginationDTO.of(page, size, sortBy, direction),
                        Optional.ofNullable(difficulty)
                )
                .map(WordSuggestionMapper::toDTO);

        return ResponseApi
                .okResponse(suggestions, "Sugestões de palavras buscadas com sucesso.");
    }

    @GetMapping("/period")
    public ResponseEntity<ApiDataResponse<Page<WordSuggestionResponse>>> getByPeriod(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "suggestedAt") String sortBy,
            @RequestParam(required = false) Sort.Direction direction,
            @RequestParam(required = false) String difficulty
    ){
        Page<WordSuggestionResponse> suggestions = service
                .findByPeriod(
                        FindByPeriodWordSuggestionDTO.of(startDate, endDate, difficulty),
                        PaginationDTO.of(page, size, sortBy, direction)
                )
                .map(WordSuggestionMapper::toDTO);

        return ResponseApi
                .okResponse(suggestions, "Sugestões de palavras buscadas com sucesso.");
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<WordSuggestionResponse>> register(
            @Valid @RequestBody RegisterWordSuggestionRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        WordSuggestionResponse createdSuggestion = WordSuggestionMapper
                .toDTO(service.register(userId, requestBody));

        return ResponseApi
                .createdResponse(createdSuggestion, "Sugestão de palavra cadastrada com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ){
        service.delete(id);
        return ResponseApi.noContentResponse();
    }

}
