package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.model.MatchPlayedModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchPlayedRepository extends JpaRepository<MatchPlayedModel, Long> {

}
