package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.model.WordDrawnModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordDrawnRepository extends JpaRepository<WordDrawnModel, Long> {

    @Query("""
        SELECT wd
        FROM WordDrawnModel wd
        JOIN FETCH wd.word
        WHERE wd.matchPlayed.id = :id
        ORDER BY wd.orderIndex
    """)
    List<WordDrawnModel> findByMatchPlayed_IdWithWord(Long id);

}
