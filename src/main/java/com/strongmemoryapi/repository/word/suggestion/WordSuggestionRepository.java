package com.strongmemoryapi.repository.word.suggestion;

import com.strongmemoryapi.domain.model.word.suggestion.WordSuggestionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface WordSuggestionRepository extends JpaRepository<WordSuggestionModel, Long> {

    Page<WordSuggestionModel> findBySuggestedAtBetweenAndDeletedFalse(
            Instant start,
            Instant end,
            Pageable pageable
    );

    Page<WordSuggestionModel> findByDifficulty_NameAndSuggestedAtBetweenAndDeletedFalse(
            String difficultyName,
            Instant start,
            Instant end,
            Pageable pageable
    );

    Page<WordSuggestionModel> findByDeletedFalse(Pageable pageable);

    Page<WordSuggestionModel> findByDifficulty_NameAndDeletedFalse(String difficultyName, Pageable pageable);

    Optional<WordSuggestionModel> findByIdAndDeletedFalse(Long id);

    Optional<WordSuggestionModel> findByWordAndDeletedFalse(String word);

}
