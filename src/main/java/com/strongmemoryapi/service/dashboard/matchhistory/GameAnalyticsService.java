package com.strongmemoryapi.service.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.GameHighestScoreProjection;
import com.strongmemoryapi.projection.matchhistory.GameModeAnalyticsProjection;
import com.strongmemoryapi.repository.dashboard.matchhistory.GameAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameAnalyticsService {

    @Autowired
    private GameAnalyticsRepository repository;

    public List<GameModeAnalyticsProjection> findGameModeAnalytics(Long userId){
        return repository.findGameModeAnalytics(userId);
    }

    public List<GameHighestScoreProjection> findHighestScores(Long userId){
        return repository.findHighestScores(userId);
    }

}
