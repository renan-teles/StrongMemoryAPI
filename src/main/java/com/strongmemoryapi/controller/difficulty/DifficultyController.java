package com.strongmemoryapi.controller.difficulty;

import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.response.DifficultyResponse;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.utils.mapper.DifficultyMapper;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/difficulty", produces = "application/json;charset=UTF-8")
public class DifficultyController {

    @Autowired
    private DifficultyService service;

    @GetMapping
    ResponseEntity<ApiDataResponse<List<DifficultyResponse>>> getAll(){
        List<DifficultyResponse> difficulties = service
                .findAll()
                .stream()
                .map(DifficultyMapper::toDTO)
                .toList();

        return ResponseApi.okResponse(difficulties, "Dificuldades buscadas com sucesso.");
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiDataResponse<DifficultyResponse>> getById(
            @PathVariable Byte id
    ){
        DifficultyResponse difficulty = DifficultyMapper.toDTO(service.findById(id));
        return  ResponseApi.okResponse(difficulty, "Dificuldade buscada com sucesso.");
    }

}
