package com.strongmemoryapi.repository.matchhistory;

import com.strongmemoryapi.domain.model.matchhistory.DrawnWordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DrawnWordRepository extends JpaRepository<DrawnWordModel, Long> {

    @Query("""
        SELECT dw
        FROM DrawnWordModel dw
        JOIN FETCH dw.word
        WHERE dw.matchPlayed.id = :id
        ORDER BY dw.orderIndex
    """)
    List<DrawnWordModel> findByMatchPlayed_IdWithWord(Long id);

    @Query("""
        SELECT dw
        FROM DrawnWordModel dw
        JOIN FETCH dw.word
        WHERE
            dw.matchPlayed.id = :id
            AND dw.orderIndex >= :orderIndex
        ORDER BY dw.orderIndex
    """)
    List<DrawnWordModel> findByMatchPlayed_IdWithWordAndOrderIndexEqualsOrGreaterThan(Long id, Integer orderIndex);

    @Query("""
        SELECT dw.id
        FROM DrawnWordModel dw
        WHERE dw.matchPlayed.id = :matchId
        ORDER BY dw.orderIndex ASC
    """)
    List<Long> findIdByMatchPlayed_IdOrderByOrderIndex(Long matchId);

    @Query("""
        SELECT dw.word.id
        FROM DrawnWordModel dw
        WHERE dw.matchPlayed.id = :matchId
        ORDER BY dw.orderIndex ASC
    """)
    List<Long> findWord_IdByMatchPlayed_IdOrderByOrderIndex(Long matchId);

    long countByMatchPlayed_Id(Long matchId);

}
