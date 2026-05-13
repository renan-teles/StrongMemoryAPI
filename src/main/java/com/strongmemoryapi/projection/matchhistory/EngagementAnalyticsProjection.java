package com.strongmemoryapi.projection.matchhistory;

import java.time.LocalDate;

public interface EngagementAnalyticsProjection {
    LocalDate getActivityDate();
    Long getTotalMatches();
    Long getTotalCorrectAnswers();
    Long getTotalErrors();
    Long getTotalAnswers();
}
