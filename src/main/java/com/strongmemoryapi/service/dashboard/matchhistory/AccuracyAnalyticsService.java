package com.strongmemoryapi.service.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.AccuracyByDifficultyProjection;
import com.strongmemoryapi.projection.matchhistory.AccuracySummaryProjection;
import com.strongmemoryapi.repository.dashboard.matchhistory.AccuracyAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccuracyAnalyticsService {

    @Autowired
    private AccuracyAnalyticsRepository repository;

    public AccuracySummaryProjection findAccuracySummary(Long userId){
        return repository.findAccuracySummary(userId);
    }

    public List<AccuracyByDifficultyProjection> findAccuracyByDifficulty(Long userId){
        return repository.findAccuracyByDifficulty(userId);
    }

}
