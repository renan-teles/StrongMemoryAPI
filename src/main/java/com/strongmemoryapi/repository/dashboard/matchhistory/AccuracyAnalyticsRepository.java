package com.strongmemoryapi.repository.dashboard.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.projection.matchhistory.AccuracyByDifficultyProjection;
import com.strongmemoryapi.projection.matchhistory.AccuracySummaryProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AccuracyAnalyticsRepository
        extends org.springframework.data.repository.Repository<MatchPlayedModel, Long> {


    @Query(value = """
        SELECT
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
                    (
                        SUM(number_errors) * 100.0
                    ) /
                    NULLIF(
                        SUM(number_correct_answers + number_errors),
                        0
                    ),
                    2
                ),
                0
            ) AS overallErrorPercentage,
            COALESCE(
                ROUND(
                    AVG(accuracy_percentage),
                    2
                ),
                0
            ) AS averageAccuracyPercentage,
            COALESCE(
                ROUND(
                    100 - AVG(accuracy_percentage),
                    2
                ),
                0
            ) AS averageErrorPercentage
        FROM matches_played
        WHERE user_id = :userId;
    """, nativeQuery = true)
    AccuracySummaryProjection findAccuracySummary(@Param("userId") Long userId);


    @Query(value = """
        SELECT
            difficulty_name AS difficulty,
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
        GROUP BY difficulty_name
        ORDER BY overallAccuracyPercentage DESC;
    """, nativeQuery = true)
    List<AccuracyByDifficultyProjection> findAccuracyByDifficulty(@Param("userId") Long userId);


}
