package com.strongmemoryapi.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "drawn_words")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class DrawnWordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "order_index")
    private Integer orderIndex;

    @Column(name = "last_typed_word", length = 180)
    private String lastTypedWord;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "was_shown")
    private Boolean wasShown;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private WordModel word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_played_id")
    private MatchPlayedModel matchPlayed;

    public String getWordString(){
        return word.getWord();
    }

    public Long getWordId(){
        return word.getId();
    }

}
