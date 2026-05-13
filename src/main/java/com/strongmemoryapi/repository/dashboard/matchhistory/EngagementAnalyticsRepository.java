package com.strongmemoryapi.repository.dashboard.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.projection.matchhistory.EngagementAnalyticsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EngagementAnalyticsRepository
        extends org.springframework.data.repository.Repository<MatchPlayedModel, Long> {


    @Query(value = """
        SELECT
             COALESCE(DATE(start_playing_at)) AS activityDate,
             COUNT(*) AS totalMatches,
             COALESCE(SUM(number_correct_answers),0) AS totalCorrectAnswers,
             COALESCE(SUM(number_errors),0) AS totalErrors,
             COALESCE(SUM(number_errors + number_correct_answers),0) AS totalAnswers
        FROM matches_played
        WHERE
            user_id = :userId
            AND start_playing_at >= NOW() - INTERVAL :days DAY
        GROUP BY DATE(start_playing_at)
        ORDER BY activityDate;
    """, nativeQuery = true)
    List<EngagementAnalyticsProjection> findEngagementAnalytics(
            @Param("userId") Long userId,
            @Param("days") Integer days
    );


}
