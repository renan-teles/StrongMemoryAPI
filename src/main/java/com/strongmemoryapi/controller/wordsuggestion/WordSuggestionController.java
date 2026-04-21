package com.strongmemoryapi.controller.wordsuggestion;

import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.request.wordsuggestion.WordSuggestionRequest;
import com.strongmemoryapi.dto.response.WordSuggestionResponse;
import com.strongmemoryapi.service.wordsuggestion.WordSuggestionService;
import com.strongmemoryapi.utils.mapper.WordSuggestionMapper;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import com.strongmemoryapi.utils.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/word-suggestion", produces = "application/json;charset=UTF-8")
public class WordSuggestionController {

    @Autowired
    private WordSuggestionService service;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<WordSuggestionResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "suggestedWord") String sortBy
    ){
        Page<WordSuggestionResponse> suggestions = service
                .findAll(page, size, sortBy)
                .map(WordSuggestionMapper::toDTO);

        return ResponseApi
                .okResponse(suggestions, "Sugestões de palavras buscadas com sucesso.");
    }

    @GetMapping("/period")
    public ResponseEntity<ApiDataResponse<Page<WordSuggestionResponse>>> getByPeriod(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<WordSuggestionResponse> suggestions = service
                .findByPeriod(startDate, endDate, page, size).map(WordSuggestionMapper::toDTO);

        return ResponseApi
                .okResponse(suggestions, "Sugestões de palavras buscadas com sucesso.");
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<WordSuggestionResponse>> register(
            @Valid @RequestBody WordSuggestionRequest request
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        WordSuggestionResponse createdSuggestion = WordSuggestionMapper
                .toDTO(service.register(userId, request));

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
