package com.strongmemoryapi.domain.model;

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
    @Column(name = "start_playing_in", nullable = false, updatable = false)
    private Instant startPlayingIn;

    @Column(name = "stopped_playing_in")
    private Instant stoppedPlayingIn;

    @Column(nullable = false, name = "infinite_mode")
    private Boolean infiniteMode;

    @Column(name =  "number_correct_answers")
    private Integer numberCorrectAnswers;

    @Column(name =  "number_errors")
    private Integer numberErrors;

    @Column(name = "score_achieved")
    private Integer scoreAchieved;

    @Column(precision = 5, scale = 2)
    private BigDecimal accuracy;

    @Column(name = "avg_response_time_ms")
    private Integer avgResponseTimeMs;

    @Column(name = "gave_up")
    private Boolean gaveUp;

    @Column(name = "completed_sequence_words")
    private boolean completedSequenceWords;

    @Column(name = "finished_by_timeout")
    private boolean finishedByTimeout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficulty_name")
    private DifficultyModel difficulty;

    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matchPlayed", fetch = FetchType.LAZY)
    private List<WordDrawnModel> wordDrawnList;

}
