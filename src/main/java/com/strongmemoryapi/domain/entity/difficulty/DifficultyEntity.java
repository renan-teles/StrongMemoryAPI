package com.strongmemoryapi.domain.entity.difficulty;

import com.strongmemoryapi.domain.entity.word.WordEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "difficulties")
public class DifficultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "difficulty", cascade = CascadeType.ALL)
    private List<WordEntity> words;

    @Column(unique = true, nullable = false)
    private String difficulty;

    @Column(unique = true, nullable = false)
    private String translation;

    @Column(nullable = false, name = "max_quantity_words")
    private Byte maxQuantityWords;

    @Column(nullable = false, name = "increase_display_time_seconds")
    private Byte increaseDisplayTimeSeconds;

    @Column(nullable = false, name = "increase_typing_time_seconds")
    private Byte increaseTypingTimeSeconds;

    public Byte getId() {
        return id;
    }

    public List<WordEntity> getWords() {
        return words;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Byte getMaxQuantityWords() {
        return maxQuantityWords;
    }

    public void setMaxQuantityWords(Byte maxQuantityWords) {
        this.maxQuantityWords = maxQuantityWords;
    }

    public Byte getIncreaseDisplayTimeSeconds() {
        return increaseDisplayTimeSeconds;
    }

    public void setIncreaseDisplayTimeSeconds(Byte increaseDisplayTimeSeconds) {
        this.increaseDisplayTimeSeconds = increaseDisplayTimeSeconds;
    }

    public Byte getIncreaseTypingTimeSeconds() {
        return increaseTypingTimeSeconds;
    }

    public void setIncreaseTypingTimeSeconds(Byte increaseTypingTimeSeconds) {
        this.increaseTypingTimeSeconds = increaseTypingTimeSeconds;
    }

}
