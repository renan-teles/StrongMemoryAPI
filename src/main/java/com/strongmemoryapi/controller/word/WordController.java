package com.strongmemoryapi.controller.word;

import com.strongmemoryapi.dto.response.ApiResponse;
import com.strongmemoryapi.dto.request.word.WordRegistrationRequest;
import com.strongmemoryapi.dto.request.word.WordUpdateRequest;
import com.strongmemoryapi.dto.response.WordResponse;
import com.strongmemoryapi.service.word.WordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/word")
public class WordController {

    @Autowired
    private WordService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WordResponse> register(@Valid @RequestBody WordRegistrationRequest request){
        WordResponse res = service.register(request);
        return new ApiResponse<>(201, "Palavra cadastrada com sucesso.", res);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody WordUpdateRequest request){
        service.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("/get-random-list")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<WordResponse>> getRandomWords(
            @RequestParam String difficulty,
            @RequestParam(name = "quantity") int wordsQuantity
    ){
        List<WordResponse> res = service.getRandomWords(difficulty, wordsQuantity);
        return new ApiResponse<>(200, "Palavras sorteadas com sucesso.", res);
    }

    @GetMapping("/get-by-difficulty")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<WordResponse>> getAllByDifficulty(
            @RequestParam(defaultValue = "easy") String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "word") String sortBy
    ){
        Page<WordResponse> res = service.getAllByDifficulty(difficulty, page, size, sortBy);
        return new ApiResponse<>(200, "Palavras buscadas com sucesso.", res);
    }

}
