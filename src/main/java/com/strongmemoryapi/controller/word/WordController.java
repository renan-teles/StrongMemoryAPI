package com.strongmemoryapi.controller.word;

import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.request.word.WordRegistrationRequest;
import com.strongmemoryapi.dto.request.word.WordUpdateRequest;
import com.strongmemoryapi.dto.response.WordResponse;
import com.strongmemoryapi.service.word.WordService;
import com.strongmemoryapi.utils.mapper.WordMapper;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/word", produces = "application/json;charset=UTF-8")
public class WordController {

    @Autowired
    private WordService service;

    @PostMapping
    public ResponseEntity<ApiDataResponse<WordResponse>> register(
            @Valid @RequestBody WordRegistrationRequest request
    ){
        WordResponse createdWord = WordMapper.toDTO(service.register(request));
        return ResponseApi
                .createdResponse(createdWord, "Palavra cadastrada com sucesso.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody WordUpdateRequest request
    ){
        service.update(id, request);
        return ResponseApi.noContentResponse();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseApi.noContentResponse();
    }

    @GetMapping("/random-list")
    public ResponseEntity<ApiDataResponse<List<WordResponse>>> getRandomList(
            @RequestParam String difficulty,
            @RequestParam int quantity
    ){
        List<WordResponse> randomWords = service
                .findRandomWords(difficulty, quantity)
                .stream()
                .map(WordMapper::toDTO)
                .toList();

        return ResponseApi
                .okResponse(randomWords, "Palavras sorteadas com sucesso.");
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<WordResponse>>> getByDifficulty(
            @RequestParam(defaultValue = "easy") String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "word") String sortBy
    ){
        Page<WordResponse> wordsByDifficulty = service
                .findByDifficulty(difficulty, page, size, sortBy)
                .map(WordMapper::toDTO);

        return ResponseApi
                .okResponse(wordsByDifficulty, "Palavras buscadas com sucesso.");
    }

}
