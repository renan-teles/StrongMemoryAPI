package com.strongmemoryapi.service.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.MatchDurationProjection;
import com.strongmemoryapi.projection.matchhistory.MatchResultProjection;
import com.strongmemoryapi.repository.dashboard.matchhistory.MatchAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchAnalyticsService {

    @Autowired
    private MatchAnalyticsRepository repository;

    public List<MatchResultProjection> findMatchesResults(Long userId){
        return repository.findMatchesResults(userId);
    }

    public List<MatchDurationProjection> findMatchesDuration(
            Long userId,
            Integer days
    ){
        return repository.findMatchesDuration(userId, days);
    }

}
