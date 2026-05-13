package com.strongmemoryapi.repository.matchhistory;

import com.strongmemoryapi.domain.enums.MatchMode;
import com.strongmemoryapi.domain.enums.MatchResult;
import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MatchPlayedRepository extends JpaRepository<MatchPlayedModel, Long> {

    Optional<MatchPlayedModel> findByIdAndUser_IdAndResultAndStoppedPlayingAtNull(
            Long id,
            Long userId,
            MatchResult result
    );

    @Query(value = """
        SELECT
            MAX(mp.score_achieved) AS highest_score
        FROM matches_played mp
        WHERE
            mp.user_id = :userId
            AND mp.mode = :mode
            AND mp.difficulty_name = :difficultyName
            AND mp.result IN ('COMPLETED', 'TIMEOUT', 'NOT_COMPLETED')
    """, nativeQuery = true)
    Optional<Integer> findMaxScoreByUser_IdAndModeAnDifficulty_Name(
            @Param("userId") Long userId,
            @Param("mode") MatchMode mode,
            @Param("difficultyName") String difficultyName
    );

}
