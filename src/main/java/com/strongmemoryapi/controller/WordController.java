package com.strongmemoryapi.controller;

import com.strongmemoryapi.domain.model.WordModel;
import com.strongmemoryapi.dto.PaginationDTO;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.request.word.RegisterWordRequest;
import com.strongmemoryapi.dto.request.word.UpdateWordRequest;
import com.strongmemoryapi.dto.response.WordResponse;
import com.strongmemoryapi.service.word.WordService;
import com.strongmemoryapi.service.word.WordSharedService;
import com.strongmemoryapi.utils.mapper.WordMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/word", produces = "application/json;charset=UTF-8")
public class WordController {

    @Autowired
    private WordService service;

    @Autowired
    private WordSharedService wordSharedService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<WordResponse>> register(
            @Valid @RequestBody RegisterWordRequest requestBody,
            @RequestParam(defaultValue = "false") boolean suggestionOrigin
    ){
        WordModel created = wordSharedService.register(requestBody, suggestionOrigin);
        WordResponse createdRes = WordMapper.toDTO(created);

        return ResponseApi
                .createdResponse(createdRes, "Palavra cadastrada com sucesso.");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWordRequest requestBody
    ){
        service.update(id, requestBody);
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
            @RequestParam(defaultValue = "fácil") String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "word") String sortBy,
            @RequestParam(required = false) Sort.Direction direction
    ){
        Page<WordResponse> wordsByDifficulty = service
                .findByDifficulty(
                        difficulty,
                        PaginationDTO.of(page, size, sortBy, direction)
                )
                .map(WordMapper::toDTO);

        return ResponseApi
                .okResponse(wordsByDifficulty, "Palavras buscadas com sucesso.");
    }

}
