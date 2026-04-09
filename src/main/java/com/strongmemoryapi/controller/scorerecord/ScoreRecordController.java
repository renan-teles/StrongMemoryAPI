package com.strongmemoryapi.controller.scorerecord;

import com.strongmemoryapi.dto.response.ApiResponse;
import com.strongmemoryapi.dto.request.scorerecord.ScoreRecordRequest;
import com.strongmemoryapi.dto.response.ScoreRecordResponse;
import com.strongmemoryapi.service.scorerecord.ScoreRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/score-record", produces = "application/json;charset=UTF-8")
public class ScoreRecordController {

    @Autowired
    private ScoreRecordService service;

    @GetMapping("/get-user-scores")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<List<ScoreRecordResponse>> getUserScores(
        @RequestParam(required = true) Long userId
    ){
        List<ScoreRecordResponse> res = service.getUserScoreRecords(userId);
        return new ApiResponse<>(200, "Pontuações buscadas com sucesso.", res);
    }

    @GetMapping("/get-user-score")
    @ResponseStatus(HttpStatus.OK)
    ApiResponse<ScoreRecordResponse> getUserScore(
            @RequestParam(required = true) String difficulty,
            @RequestParam(required = true) Long userId
    ){
        ScoreRecordResponse res = service.getUserScoreRecord(userId, difficulty);
        return new ApiResponse<>(200, "Pontuação buscada com sucesso.", res);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    ApiResponse<ScoreRecordResponse> updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody ScoreRecordRequest request
    ){
        ScoreRecordResponse res =  service.updateScoreRecord(id, request);
        return new ApiResponse<>(200, "Pontuação atualizada com sucesso.", res);
    }

}
