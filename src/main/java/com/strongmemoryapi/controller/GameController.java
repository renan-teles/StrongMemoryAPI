package com.strongmemoryapi.controller;

import com.strongmemoryapi.dto.request.game.StartGameRequest;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.response.GameResponse;
import com.strongmemoryapi.security.SecurityUtils;
import com.strongmemoryapi.service.game.GameService;
import com.strongmemoryapi.utils.response.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService service;

    @PostMapping
    public ResponseEntity<ApiDataResponse<GameResponse>> startGame(
          @Valid @RequestBody StartGameRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        GameResponse response = service.startGame(userId, requestBody);

        return ResponseApi
                .createdResponse(response, "Dados iniciais da partida registrados com sucesso.");
    }

}
