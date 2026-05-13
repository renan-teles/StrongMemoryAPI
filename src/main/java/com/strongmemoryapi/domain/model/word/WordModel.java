package com.strongmemoryapi.domain.model.word;

import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.matchhistory.DrawnWordModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "words")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WordModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String word;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(nullable = false, name = "is_deleted")
    private Boolean deleted;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "difficulty_name")
    private DifficultyModel difficulty;

    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "word")
    private List<DrawnWordModel> drawnList;

    public String getDifficultyName(){
        return difficulty.getName();
    }

}
