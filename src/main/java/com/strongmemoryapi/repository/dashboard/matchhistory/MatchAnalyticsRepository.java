package com.strongmemoryapi.repository.dashboard.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.projection.matchhistory.MatchDurationProjection;
import com.strongmemoryapi.projection.matchhistory.MatchResultProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MatchAnalyticsRepository
        extends org.springframework.data.repository.Repository<MatchPlayedModel, Long> {


    @Query(value = """
        SELECT
            COALESCE(result) AS matchResult,
            COUNT(*) AS totalMatches,
            COALESCE(
                ROUND(
                    (COUNT(*) * 100.0) /
                    (
                        SELECT COUNT(*)
                        FROM matches_played
                        WHERE user_id = :userId
                    ),
                    2
                )
           ) AS percentage
        FROM matches_played
        WHERE user_id = :userId
        GROUP BY result
        ORDER BY totalMatches DESC;
    """, nativeQuery = true)
    List<MatchResultProjection> findMatchesResults(@Param("userId") Long userId);


    @Query(value = """
        SELECT
              COALESCE(DATE(start_playing_at)) AS matchDate,
              COALESCE(ROUND(AVG(duration_ms)), 0) AS averageDurationMS,
              COALESCE(MIN(duration_ms),0) AS shortestMatchDurationMS,
              COALESCE(MAX(duration_ms),0) AS longestMatchDurationMS,
              COUNT(*) AS totalMatches
        FROM matches_played
        WHERE user_id = :userId
              AND duration_ms IS NOT NULL
              AND start_playing_at >= NOW() - INTERVAL :days DAY
        GROUP BY DATE(start_playing_at)
        ORDER BY matchDate;
   """, nativeQuery = true)
    List<MatchDurationProjection> findMatchesDuration(
            @Param("userId") Long userId,
            @Param("days") Integer days
    );


    /* MÉDIA DE TEMPO POR DIFICULDADE:

        SELECT
			difficulty_name AS difficulty,
            ROUND(AVG(duration_ms)) AS averageDurationMS
		FROM matches_played
        WHERE user_id = 1
        GROUP BY difficulty_name
        ORDER BY averageDurationMS DESC;

    * */


}
