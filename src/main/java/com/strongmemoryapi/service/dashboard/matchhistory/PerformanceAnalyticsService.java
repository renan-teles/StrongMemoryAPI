package com.strongmemoryapi.service.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.AccuracyTimelineProjection;
import com.strongmemoryapi.projection.matchhistory.ResponseTimeTimelineProjection;
import com.strongmemoryapi.projection.matchhistory.ScoreTimelineProjection;
import com.strongmemoryapi.repository.dashboard.matchhistory.PerformanceAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceAnalyticsService {

    @Autowired
    private PerformanceAnalyticsRepository repository;

    public List<AccuracyTimelineProjection> findAccuracyTimeline(
            Long userId,
            Integer days
    ){
        return repository.findAccuracyTimeline(userId, days);
    }

    public List<ScoreTimelineProjection> findScoreTimeline(
            Long userId,
            Integer days
    ){
        return repository.findScoreTimeline(userId, days);
    }

    public List<ResponseTimeTimelineProjection> findResponseTimeTimeline(
            Long userId,
            Integer days
    ){
        return repository.findResponseTimeTimeline(userId, days);
    }

}
