package com.strongmemoryapi.controller;

import com.strongmemoryapi.dto.game.FinishGameResponse;
import com.strongmemoryapi.dto.game.StartGameRequest;
import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.dto.game.GameDataDTO;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import com.strongmemoryapi.security.SecurityUtils;
import com.strongmemoryapi.service.game.GameService;
import com.strongmemoryapi.utils.mapper.DrawnWordMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService service;

    @PostMapping("/start")
    public ResponseEntity<ApiDataResponse<GameDataDTO>> startGame(
          @Valid @RequestBody StartGameRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        GameDataDTO response = service.start(userId, requestBody);

        return ResponseApi
                .createdResponse(
                        response,
                        "Dados iniciais da partida registrados com sucesso."
                );
    }

    @PostMapping("/gave-up")
    public ResponseEntity<Void> gaveUpGame(
            @Valid @RequestBody GameDataDTO requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        service.gaveUp(userId, requestBody);

        return ResponseApi.noContentResponse();
    }

    @PostMapping("/finish")
    public ResponseEntity<ApiDataResponse<FinishGameResponse>> finishGame(
            @Valid @RequestBody GameDataDTO requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        FinishGameResponse res = service.finish(userId, requestBody);

        return ResponseApi
                .okResponse(res, "Partida finalizada com sucesso.");
    }

    @PostMapping("/more-random-words/{matchId}")
    public ResponseEntity<ApiDataResponse<List<DrawnWordDTO>>> moreRandomWords(
            @PathVariable Long matchId,
            @RequestParam Integer startOrderIndex
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        List<DrawnWordDTO> res = service
                .findMoreRandomWords(userId, matchId, startOrderIndex)
                .stream()
                .map(DrawnWordMapper::toDTO)
                .toList();

        return ResponseApi
                .okResponse(res, "Novas palavras sorteadas com sucesso.");
    }

}
