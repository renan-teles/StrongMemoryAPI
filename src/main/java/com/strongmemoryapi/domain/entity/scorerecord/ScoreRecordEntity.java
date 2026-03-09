package com.strongmemoryapi.domain.entity.scorerecord;

import com.strongmemoryapi.domain.entity.difficulty.DifficultyEntity;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_score_records")
public class ScoreRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_id")
    private DifficultyEntity difficulty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Integer score;

    public Long getId() {
        return id;
    }

    public DifficultyEntity getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyEntity difficulty) {
        this.difficulty = difficulty;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
