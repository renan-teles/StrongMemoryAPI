package com.strongmemoryapi.repository.dashboard.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.projection.matchhistory.GameHighestScoreProjection;
import com.strongmemoryapi.projection.matchhistory.GameModeAnalyticsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface GameAnalyticsRepository
        extends org.springframework.data.repository.Repository<MatchPlayedModel, Long> {


    @Query(value = """
        SELECT
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
                ) AS averageAccuracyPercentage,
                COALESCE(
                    ROUND(
                        AVG(duration_ms)
                    ),
                    0
                ) AS averageDurationMS,
                COALESCE(
                    ROUND(
                        AVG(average_response_time_ms)
                    ),
                    0
                ) AS averageResponseTimeMS
        FROM matches_played
        WHERE user_id = :userId
        GROUP BY matchMode;
    """, nativeQuery = true)
    List<GameModeAnalyticsProjection> findGameModeAnalytics(@Param("userId") Long userId);


    @Query(value = """
        SELECT
             difficulty_name as difficulty,
             mode as matchMode,
             COALESCE(MAX(score_achieved), 0) as highestScore,
             COALESCE(DATE(start_playing_at)) as matchDate
        FROM matches_played
        WHERE
             user_id = :userId
             AND result IN ('TIMEOUT', 'COMPLETED', 'NOT_COMPLETED')
        GROUP BY
                matchDate,
                difficulty,
                matchMode;
    """, nativeQuery = true)
    List<GameHighestScoreProjection> findHighestScores(@Param("userId") Long userId);


}

