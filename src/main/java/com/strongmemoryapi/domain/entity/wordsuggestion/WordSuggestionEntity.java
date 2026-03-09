package com.strongmemoryapi.domain.entity.wordsuggestion;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "word_suggestions")
public class WordSuggestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "suggested_word", nullable = false)
    private String suggestedWord;

    @Column(name = "suggested_difficulty", nullable = false)
    private String suggestedDifficulty;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "suggested_at")
    private Instant suggestedAt;

    public Long getId() {
        return id;
    }

    public void setUser(UserEntity user){
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }

    public void setSuggestedWord(String suggestedWord) {
        this.suggestedWord = suggestedWord;
    }

    public String getSuggestedDifficulty() {
        return suggestedDifficulty;
    }

    public void setSuggestedDifficulty(String suggestedDifficulty) {
        this.suggestedDifficulty = suggestedDifficulty;
    }

    public Instant getSuggestedAt() {
        return suggestedAt;
    }
}
