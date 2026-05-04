package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.model.DifficultyModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyRepository extends JpaRepository<DifficultyModel, String> {

    Optional<DifficultyModel> findByName(String name);

}
