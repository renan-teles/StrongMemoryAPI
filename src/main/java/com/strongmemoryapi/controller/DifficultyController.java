package com.strongmemoryapi.controller;

import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.dto.difficulty.DifficultyResponse;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.utils.mapper.DifficultyMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/difficulty", produces = "application/json;charset=UTF-8")
public class DifficultyController {

    @Autowired
    private DifficultyService service;

    @GetMapping("/all")
    ResponseEntity<ApiDataResponse<List<DifficultyResponse>>> getAll(){
        List<DifficultyResponse> difficulties = service
                .findAll()
                .stream()
                .map(DifficultyMapper::toDTO)
                .toList();

        return ResponseApi.okResponse(difficulties, "Dificuldades buscadas com sucesso.");
    }

    @GetMapping
    ResponseEntity<ApiDataResponse<DifficultyResponse>> getByName(
            @RequestParam String name
    ){
        DifficultyResponse difficulty = DifficultyMapper.toDTO(service.findByName(name));
        return ResponseApi
                .okResponse(difficulty, "Dificuldade buscada com sucesso.");
    }

}
