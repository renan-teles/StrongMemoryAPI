package com.strongmemoryapi.domain.entity.word;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_id")
    private DifficultyEntity difficulty;

    @Column(unique = true, nullable = false)
    private String word;

    public Long getId() {
        return id;
    }

    public DifficultyEntity getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyEntity difficulty) {
        this.difficulty = difficulty;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
