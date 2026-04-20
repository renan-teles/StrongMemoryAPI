package com.strongmemoryapi.repository.difficulty;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyRepository extends JpaRepository<DifficultyEntity, Byte> {

    Optional<DifficultyEntity> findByDifficulty(String difficulty);

    boolean existsByDifficulty(String difficulty);

}
