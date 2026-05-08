package com.strongmemoryapi.controller;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.dto.user.UpdatePasswordRequest;
import com.strongmemoryapi.dto.user.RegisterUserRequest;
import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.dto.user.UserResponse;
import com.strongmemoryapi.service.user.scorerecord.ScoreRecordService;
import com.strongmemoryapi.service.user.UserService;
import com.strongmemoryapi.utils.mapper.UserMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import com.strongmemoryapi.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/player", produces = "application/json;charset=UTF-8")
public class PlayerController {

    private final UserRole ROLE = UserRole.ROLE_PLAYER;

    @Autowired
    private UserService userService;

    @Autowired
    private ScoreRecordService scoreService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserResponse>> register (
            @Valid @RequestBody RegisterUserRequest requestBody
    ){
        UserResponse registeredUser = UserMapper.toDTO(userService.register(ROLE, requestBody));
        return ResponseApi
                .createdResponse(registeredUser, "Jogador cadastrado com sucesso.");
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updatePassword(userId, ROLE, requestBody);
        return ResponseApi.noContentResponse();
    }

    /*
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

    @PatchMapping("/me/score/new-score/{scoreId}")
    public ResponseEntity<ApiDataResponse<ScoreRecordResponse>> updateScore(
            @PathVariable Long scoreId,
            @Valid @RequestBody ScoreRecordRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        ScoreRecordResponse updatedScore = ScoreRecordMapper.toDTO(
                scoreService.updateScoreRecord(scoreId, userId, requestBody)
        );

        return ResponseApi
                .okResponse(updatedScore, "Pontuação atualizada com sucesso.");
    }
    */

}
