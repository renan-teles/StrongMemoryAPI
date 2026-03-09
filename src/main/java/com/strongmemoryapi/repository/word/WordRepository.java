package com.strongmemoryapi.repository.word;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.domain.entity.word.WordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long> {

    Page<WordEntity> findAllByDifficulty(Pageable pageable, DifficultyEntity difficulty);

    @Query("SELECT w.id FROM WordEntity w WHERE w.difficulty = :difficulty")
    List<Long> findIdsByDifficulty(@Param("difficulty") DifficultyEntity difficulty);

    Long countByDifficulty(DifficultyEntity difficulty);

    List<WordEntity> findByIdIn(List<Long> ids);

    boolean existsByWord(String word);

    Optional<Byte> findDifficultyIdById(Long id);

    /*
    @Query(value = """
                SELECT w.id, w.word FROM words w
                WHERE difficulty_id = :difficultyId
                ORDER BY RAND()
                LIMIT :quantity
            """,
            nativeQuery = true
    )
    List<WordEntity> findRandomWordsByDifficulty(
            @Param("difficultyId") Long difficultyId,
            @Param("quantity") int quantity
    );
    */

}
