package com.strongmemoryapi.repository;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.domain.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByIdAndRole(Long id, UserRole role);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByEmailAndRole(String email, UserRole role);

}
