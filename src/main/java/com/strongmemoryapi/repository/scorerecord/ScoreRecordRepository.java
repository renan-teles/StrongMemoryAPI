package com.strongmemoryapi.repository.scorerecord;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.domain.entity.scorerecord.ScoreRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecordEntity, Long> {

    List<ScoreRecordEntity> findByUserId(Long userId);

    Optional<ScoreRecordEntity> findByUserIdAndDifficulty(Long userId, DifficultyEntity difficulty);

    Optional<ScoreRecordEntity> findByIdAndUserId(Long id, Long userId);

}
