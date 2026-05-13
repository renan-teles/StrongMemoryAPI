package com.strongmemoryapi.service.dashboard.matchhistory;

import com.strongmemoryapi.projection.matchhistory.OverviewAnalyticsProjection;
import com.strongmemoryapi.repository.dashboard.matchhistory.OverviewAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OverviewAnalyticsService {

    @Autowired
    private OverviewAnalyticsRepository repository;

    public OverviewAnalyticsProjection findOverview(Long userId) {
        return repository.findOverview(userId);
    }

}
