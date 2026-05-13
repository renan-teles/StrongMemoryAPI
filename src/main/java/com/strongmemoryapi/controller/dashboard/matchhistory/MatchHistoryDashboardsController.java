package com.strongmemoryapi.controller.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.*;
import com.strongmemoryapi.security.SecurityUtils;
import com.strongmemoryapi.service.dashboard.matchhistory.*;
import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.utils.response.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
      value = "/api/dashboard/match-history",
      produces = "application/json;charset=UTF-8"
)
public class MatchHistoryDashboardsController {

    private final String SUCCESS_MESSAGE = "Dados buscados com sucesso.";

    @Autowired
    private OverviewAnalyticsService overviewService;

    @Autowired
    private PerformanceAnalyticsService performanceService;

    @Autowired
    private AccuracyAnalyticsService accuracyService;

    @Autowired
    private MatchAnalyticsService matchService;

    @Autowired
    private GameAnalyticsService gameService;

    @Autowired
    private EngagementAnalyticsService engagementService;

    /* OVERVIEW */
    @GetMapping("/overview")
    public ResponseEntity<ApiDataResponse<OverviewAnalyticsProjection>> getOverview() {
        Long userId = SecurityUtils.getCurrentUserId();
        OverviewAnalyticsProjection data = overviewService.findOverview(userId);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    /* PERFORMANCE */
    @GetMapping("/performance/accuracy")
    public ResponseEntity<ApiDataResponse<List<AccuracyTimelineProjection>>> getAccuracyTimeline(
            @RequestParam(defaultValue = "30") Integer days
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        List<AccuracyTimelineProjection> data =
                performanceService.findAccuracyTimeline(userId, days);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/performance/scores")
    public ResponseEntity<ApiDataResponse<List<ScoreTimelineProjection>>> getScoreTimeline(
            @RequestParam(defaultValue = "30") Integer days
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        List<ScoreTimelineProjection> data =
                performanceService.findScoreTimeline(userId, days);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/performance/response-time")
    public ResponseEntity<ApiDataResponse<List<ResponseTimeTimelineProjection>>> getResponseTimeTimeline(
            @RequestParam(defaultValue = "30") Integer days
    ){
        Long userId = SecurityUtils.getCurrentUserId();

        List<ResponseTimeTimelineProjection> data =
                performanceService.findResponseTimeTimeline(userId, days);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    /* ACCURACY */
    @GetMapping("/accuracy/summary")
    public ResponseEntity<ApiDataResponse<AccuracySummaryProjection>> getAccuracySummary(){
        Long userId = SecurityUtils.getCurrentUserId();
        AccuracySummaryProjection data =
                accuracyService.findAccuracySummary(userId);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/accuracy/by-difficulty")
    public ResponseEntity<ApiDataResponse<List<AccuracyByDifficultyProjection>>> getAccuracyByDifficulty(){
        Long userId = SecurityUtils.getCurrentUserId();
        List<AccuracyByDifficultyProjection> data =
                accuracyService.findAccuracyByDifficulty(userId);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    /* MATCHES */
    @GetMapping("/matches/results")
    public ResponseEntity<ApiDataResponse<List<MatchResultProjection>>> getMatchesResults(){
        Long userId = SecurityUtils.getCurrentUserId();
        List<MatchResultProjection> data =
                matchService.findMatchesResults(userId);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/matches/duration")
    public ResponseEntity<ApiDataResponse<List<MatchDurationProjection>>> getMatchesDuration(
            @RequestParam(defaultValue = "30") Integer days
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        List<MatchDurationProjection> data =
                matchService.findMatchesDuration(userId, days);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    /* GAME */
    @GetMapping("/game/modes")
    public ResponseEntity<ApiDataResponse<List<GameModeAnalyticsProjection>>> getGameModeData(){
        Long userId = SecurityUtils.getCurrentUserId();

        List<GameModeAnalyticsProjection> data =
                gameService.findGameModeAnalytics(userId);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    @GetMapping("/game/highest-scores")
    public ResponseEntity<ApiDataResponse<List<GameHighestScoreProjection>>> getGameHighestScores(){
        Long userId = SecurityUtils.getCurrentUserId();

        List<GameHighestScoreProjection> data =
                gameService.findHighestScores(userId);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

    /* ENGAGEMENT */
    @GetMapping("/engagement")
    public ResponseEntity<ApiDataResponse<List<EngagementAnalyticsProjection>>> getEngagement(
            @RequestParam(defaultValue = "30") Integer days
    ){
        Long userId = SecurityUtils.getCurrentUserId();

        List<EngagementAnalyticsProjection> data =
                engagementService.findEngagementAnalytics(userId, days);

        return ResponseApi.okResponse(data, SUCCESS_MESSAGE);
    }

}


