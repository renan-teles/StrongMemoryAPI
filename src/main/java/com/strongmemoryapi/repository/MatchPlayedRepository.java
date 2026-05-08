package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.model.MatchPlayedModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchPlayedRepository extends JpaRepository<MatchPlayedModel, Long> {

    Optional<MatchPlayedModel> findByIdAndUser_IdAndGaveUpFalseAndStoppedPlayingAtNull(
            Long id,
            Long userId
    );

}


