package com.strongmemoryapi.repository.wordsuggestion;

import com.strongmemoryapi.domain.entity.wordsuggestion.WordSuggestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface WordSuggestionRepository extends JpaRepository<WordSuggestionEntity, Long> {

    Page<WordSuggestionEntity> findBySuggestedAtBetween(
            Instant start,
            Instant end,
            Pageable pageable
    );

}
