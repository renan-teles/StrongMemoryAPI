package com.strongmemoryapi.controller.difficulty;

import com.strongmemoryapi.dto.response.ApiResponse;
import com.strongmemoryapi.dto.response.DifficultyResponse;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/difficulty")
public class DifficultyController {

    @Autowired
    private DifficultyService service;

    @GetMapping("/get-all")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<List<DifficultyResponse>> getAll(){
        List<DifficultyResponse> difficults = service.getAll();
        return new ApiResponse<>(200, "Dificuldades buscadas com sucesso.", difficults);
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<DifficultyResponse> getById(@PathVariable Byte id){
        DifficultyResponse difficulty = service.getById(id);
        return new ApiResponse<>(200, "Dificuldade buscada com sucesso.", difficulty);
    }

}
