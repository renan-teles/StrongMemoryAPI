package com.strongmemoryapi.controller.wordsuggestion;

import com.strongmemoryapi.dto.response.ApiResponse;
import com.strongmemoryapi.dto.request.wordsuggestion.WordSuggestionRequest;
import com.strongmemoryapi.dto.response.WordSuggestionResponse;
import com.strongmemoryapi.service.wordsuggestion.WordSuggestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/word-suggestion")
public class WordSuggestionController {

    @Autowired
    private WordSuggestionService service;

    @GetMapping("get-all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<WordSuggestionResponse>> getAllByDifficulty(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id", name = "sort_by") String sortBy
    ){
        Page<WordSuggestionResponse> res = service.getAll(page, size, sortBy);
        return new ApiResponse<>(200, "Sugestões buscadas com sucesso.", res);
    }

    @GetMapping("get-all-by-period")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<WordSuggestionResponse>> getAllByPeriod(
            @RequestParam(name = "start_date") LocalDate startDate,
            @RequestParam(name = "end_date") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<WordSuggestionResponse> res = service.getByPeriod(startDate, endDate, page, size);
        return new ApiResponse<>(200, "Sugestões buscadas com sucesso.", res);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WordSuggestionResponse> register(
            @RequestParam(name = "user_id") Long userId,
            @Valid @RequestBody WordSuggestionRequest request
    ){
        WordSuggestionResponse res = service.register(userId, request);
        return new ApiResponse<>(201, "Sugestão de palavra cadastrada com sucesso.", res);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
