package com.strongmemoryapi.domain.entity.difficulty;

import com.strongmemoryapi.domain.entity.word.WordEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "difficults")
public class DifficultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "difficulty", cascade = CascadeType.ALL)
    private List<WordEntity> words;

    @Column(unique = true, nullable = false)
    private String difficulty;

    public Byte getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<WordEntity> getWords() {
        return words;
    }

}
