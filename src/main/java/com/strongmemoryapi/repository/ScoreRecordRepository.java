package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.model.ScoreRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecordModel, Long> {

    Optional<ScoreRecordModel> findByUser_IdAndDifficulty_NameAndInfiniteMode(
            Long userId,
            String difficultyName,
            boolean infiniteMode
    );

    /*
    List<ScoreRecordModel> findByUser_Id(Long userId);

    Optional<ScoreRecordModel> findByIdAndUser_Id(Long id, Long userId);
    */
}
