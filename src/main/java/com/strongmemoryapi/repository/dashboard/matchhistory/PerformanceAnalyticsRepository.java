package com.strongmemoryapi.repository.dashboard.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.projection.matchhistory.AccuracyTimelineProjection;
import com.strongmemoryapi.projection.matchhistory.ResponseTimeTimelineProjection;
import com.strongmemoryapi.projection.matchhistory.ScoreTimelineProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PerformanceAnalyticsRepository
        extends org.springframework.data.repository.Repository<MatchPlayedModel, Long> {


    @Query(value = """
        SELECT
            COALESCE(DATE(start_playing_at)) AS matchDate,
            mode AS matchMode,
            COUNT(*) AS totalMatches,
            COALESCE(
                SUM(number_correct_answers),
                0
            ) AS totalCorrectAnswers,
            COALESCE(
                 SUM(number_errors),
                0
            ) AS totalErrors,
            COALESCE(
                ROUND(
                    (
                        SUM(number_correct_answers) * 100.0
                    ) /
                    NULLIF(
                        SUM(number_correct_answers + number_errors),
                        0
                    ),
                    2
                ),
                0
            ) AS overallAccuracyPercentage,
            COALESCE(
                ROUND(
                    AVG(accuracy_percentage),
                    2
                ),
                0
            ) AS averageAccuracyPercentage
        FROM matches_played
        WHERE
            user_id = :userId
            AND accuracy_percentage IS NOT NULL
            AND start_playing_at >= NOW() - INTERVAL :days DAY
        GROUP BY
            DATE(start_playing_at),
            matchMode
        ORDER BY matchDate;
    """, nativeQuery = true)
    List<AccuracyTimelineProjection> findAccuracyTimeline(
            @Param("userId") Long userId,
            @Param("days") Integer days
    );


    @Query(value = """
        SELECT
            COALESCE(DATE(start_playing_at)) AS matchDate,
            difficulty_name AS difficulty,
            COALESCE(MAX(score_achieved),0) AS highestScore,
            COALESCE(
                ROUND(
                    AVG(score_achieved),
                    2
                ),
                0
            ) AS averageScore,
            COUNT(*) AS totalMatches
        FROM matches_played
        WHERE
            user_id = :userId
            AND start_playing_at >= NOW() - INTERVAL :days DAY
        GROUP BY
            DATE(start_playing_at),
            difficulty_name
        ORDER BY matchDate;
    """, nativeQuery = true)
    List<ScoreTimelineProjection> findScoreTimeline(
            @Param("userId") Long userId,
            @Param("days") Integer days
    );


    @Query(value = """
        SELECT
            COALESCE(DATE(start_playing_at)) AS matchDate,
            COUNT(*) AS totalMatches,
            COALESCE(
                ROUND(
                    AVG(average_response_time_ms),
                    2
                ),
                0
            ) AS averageResponseTimeMS,
            COALESCE(MIN(average_response_time_ms),0) AS fastestResponseTimeMS,
            COALESCE(MAX(average_response_time_ms),0) AS slowestResponseTimeMS
        FROM matches_played
        WHERE
            user_id = :userId
            AND average_response_time_ms IS NOT NULL
            AND start_playing_at >= NOW() - INTERVAL :days DAY
        GROUP BY DATE(start_playing_at)
        ORDER BY matchDate;
    """, nativeQuery = true)
    List<ResponseTimeTimelineProjection> findResponseTimeTimeline(
            @Param("userId") Long userId,
            @Param("days") Integer days
    );

}
