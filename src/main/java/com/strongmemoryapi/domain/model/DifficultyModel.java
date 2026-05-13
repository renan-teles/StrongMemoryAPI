package com.strongmemoryapi.domain.model;

import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.domain.model.word.WordModel;
import com.strongmemoryapi.domain.model.word.suggestion.WordSuggestionModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "difficulties")
@Getter
@NoArgsConstructor
public class DifficultyModel {

    @Setter
    @Id
    @Column(unique = true, nullable = false, length = 25, updatable = false)
    private String name;

    @Column(nullable = false, name = "increase_per_hit", updatable = false)
    private Integer increasePerHit;

    @Column(nullable = false, name = "number_words", updatable = false)
    private Integer numberWords;

    @Column(nullable = false, name = "increase_display_time_seconds", updatable = false)
    private Integer increaseDisplayTimeSeconds;

    @Column(nullable = false, name = "increase_typing_time_seconds", updatable = false)
    private Integer increaseTypingTimeSeconds;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "difficulty", cascade = CascadeType.ALL)
    private List<WordModel> words;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "difficulty", cascade = CascadeType.ALL)
    private List<WordSuggestionModel> wordSuggestions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "difficulty", cascade = CascadeType.ALL)
    private List<MatchPlayedModel> matchesPlayed;

}
