package com.strongmemoryapi.domain.model.matchhistory;

import com.strongmemoryapi.domain.enums.MatchMode;
import com.strongmemoryapi.domain.enums.MatchResult;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.UserModel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "matches_played")
@Setter
@Getter
@NoArgsConstructor
public class MatchPlayedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(name = "start_playing_at", nullable = false, updatable = false)
    private Instant startPlayingAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchMode mode;

    @Column(name = "stopped_playing_at")
    private Instant stoppedPlayingAt;

    @Column(name = "total_words")
    private Integer totalWords;

    @Column(name =  "number_correct_answers")
    private Integer numberCorrectAnswers;

    @Column(name =  "number_errors")
    private Integer numberErrors;

    @Column(name = "score_achieved")
    private Integer scoreAchieved;

    @Column(precision = 5, scale = 2)
    private BigDecimal accuracyPercentage;

    @Column(name = "average_response_time_ms")
    private Integer averageResponseTimeMs;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchResult result;

    @Column(name = "duration_ms")
    private Long durationMs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "difficulty_name")
    private DifficultyModel difficulty;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchPlayed", fetch = FetchType.LAZY)
    private List<DrawnWordModel> wordDrawnList;

    public boolean isInInfiniteMode(){
        if(mode == null) return false;
        return mode.equals(MatchMode.INFINITE);
    }

    public boolean isInFiniteMode(){
        if(mode == null) return false;
        return mode.equals(MatchMode.FINITE);
    }

}
