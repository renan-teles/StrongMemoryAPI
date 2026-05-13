package com.strongmemoryapi.service.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.EngagementAnalyticsProjection;
import com.strongmemoryapi.repository.dashboard.matchhistory.EngagementAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngagementAnalyticsService {

    @Autowired
    private EngagementAnalyticsRepository repository;

    public List<EngagementAnalyticsProjection> findEngagementAnalytics(
            Long userId,
            Integer days
    ){
        return repository.findEngagementAnalytics(userId, days);
    }

}
