package com.strongmemoryapi.controller.user.player;

import com.strongmemoryapi.dto.request.scorerecord.ScoreRecordRequest;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.ScoreRecordResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.service.scorerecord.ScoreRecordService;
import com.strongmemoryapi.service.user.player.PlayerAbstractUserService;
import com.strongmemoryapi.utils.mapper.ScoreRecordMapper;
import com.strongmemoryapi.utils.mapper.UserMapper;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import com.strongmemoryapi.utils.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/player", produces = "application/json;charset=UTF-8")
public class PlayerUserController {

    @Autowired
    private PlayerAbstractUserService playerService;

    @Autowired
    private ScoreRecordService scoreService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserResponse>> register (
            @Valid @RequestBody UserRequest request
    ){
        UserResponse registeredUser = UserMapper.toDTO(playerService.register(request));

        return ResponseApi
                .createdResponse(registeredUser, "Jogador cadastrado com sucesso.");
    }

    @PostMapping("/auth")
    public ResponseEntity<ApiDataResponse<AuthResponse>> auth(
            @Valid @RequestBody AuthRequest request
    ){
        AuthResponse token = playerService.auth(request);

        return ResponseApi
                .okResponse(token, "Autenticação de jogador realizada com sucesso.");
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UserPasswordUpdateRequest request
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        playerService.updatePassword(userId, request);

        return ResponseApi.noContentResponse();
    }

    @GetMapping("/me/score")
    public ResponseEntity<ApiDataResponse<ScoreRecordResponse>> getPlayerScore(
            @RequestParam String difficulty
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        ScoreRecordResponse score = ScoreRecordMapper.toDTO(
                scoreService.findUserScoreRecord(userId, difficulty)
        );

        return ResponseApi
                .okResponse(score, "Pontuação buscada com sucesso.");
    }

    @GetMapping("/me/scores")
    public ResponseEntity<ApiDataResponse<List<ScoreRecordResponse>>> getPlayerScores(){
        Long userId = SecurityUtils.getCurrentUserId();
        List<ScoreRecordResponse> scores = scoreService
                .findUserScoreRecords(userId)
                .stream()
                .map(ScoreRecordMapper::toDTO)
                .toList();

        return ResponseApi
                .okResponse(scores, "Pontuações buscadas com sucesso.");
    }

    @PutMapping("/me/new-score/{scoreId}")
    public ResponseEntity<ApiDataResponse<ScoreRecordResponse>> updateScore(
            @PathVariable Long scoreId,
            @Valid @RequestBody ScoreRecordRequest request
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        ScoreRecordResponse updatedScore = ScoreRecordMapper.toDTO(
                scoreService.updateScoreRecord(scoreId, userId, request)
        );

        return ResponseApi
                .okResponse(updatedScore, "Pontuação atualizada com sucesso.");
    }

}
