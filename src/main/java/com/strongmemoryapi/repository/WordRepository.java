package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.model.WordModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordModel, Long> {

    boolean existsByWord(String word);

    List<WordModel> findByIdInAndDeletedFalse(List<Long> ids);

    Optional<WordModel> findByIdAndDeletedFalse(Long id);

    Page<WordModel> findByDifficulty_NameAndDeletedFalse(String difficultyName, Pageable pageable);

    @Query("""
            SELECT w.id
            FROM WordModel w
            WHERE
                w.difficulty.name = :difficultyName
                AND w.deleted = false
    """)
    List<Long> findIdsByDifficulty_NameAndDeletedFalse(@Param("difficultyName") String difficultyName);

    Optional<WordModel> findByWord(String word);

}
