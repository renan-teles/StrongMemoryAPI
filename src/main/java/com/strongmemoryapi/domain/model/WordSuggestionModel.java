package com.strongmemoryapi.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "word_suggestions")
@Setter
@Getter
@NoArgsConstructor
public class WordSuggestionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String word;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "suggested_at")
    private Instant suggestedAt;

    @Column(nullable = false, name = "is_deleted")
    private Boolean deleted;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "difficulty_name")
    private DifficultyModel difficulty;

    public String getDifficultyName(){
        return difficulty.getName();
    }

}
