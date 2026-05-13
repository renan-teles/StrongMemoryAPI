package com.strongmemoryapi.repository.dashboard.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.projection.matchhistory.OverviewAnalyticsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface OverviewAnalyticsRepository
        extends org.springframework.data.repository.Repository<MatchPlayedModel, Long> {


    @Query(value = """
           SELECT
               COUNT(*) AS totalMatches,
               COALESCE(
                        ROUND(
                            AVG(number_correct_answers),
                            1
                        ),
                        0
               ) AS averageCorrectAnswersByMatch,
               COALESCE(
                        ROUND(
                            AVG(number_errors),
                            1
                        ),
                        0
               ) AS averageErrorsByMatch,
               COALESCE(
                        SUM(number_correct_answers),
                        0
               ) AS totalCorrectAnswers,
               COALESCE(
                        SUM(number_errors),
                        0
               ) AS totalErrors,
               COALESCE(
                        SUM(number_correct_answers + number_errors),
                        0
               ) AS totalAnswers,
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
                            AVG(average_response_time_ms)
                        ),
                        0
               ) AS averageResponseTimeMS,
               COALESCE(
                        ROUND(
                            AVG(duration_ms)
                        ),
                        0
               ) AS averageMatchDurationMS,
               COALESCE(
                        ROUND(
                            (
                                SUM(
                                    CASE
                                        WHEN result = 'GAVE_UP' THEN 1
                                        ELSE 0
                                    END
                                ) * 100.0
                            ) / NULLIF(COUNT(*), 0),
                            2
                        ),
                        0
               ) AS giveUpPercentage,
               COALESCE(
                        SUM(
                            CASE
                                WHEN result = 'TIMEOUT' THEN 1
                                ELSE 0
                            END
                        ),
                        0
               ) AS totalTimeoutMatches,
               COALESCE(
                        SUM(
                            CASE
                                WHEN result = 'COMPLETED' THEN 1
                                ELSE 0
                            END
                        ),
                        0
               ) AS totalCompletedMatches
           FROM matches_played
           WHERE user_id = :userId;
    """, nativeQuery = true)
    OverviewAnalyticsProjection findOverview(@Param("userId") Long userId);


}