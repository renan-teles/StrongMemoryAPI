package com.strongmemoryapi.repository.scorerecord;

import com.strongmemoryapi.domain.entity.scorerecord.ScoreRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecordEntity, Long> {

    List<ScoreRecordEntity> findByUserId(Long userId);

}
