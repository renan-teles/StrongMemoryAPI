package com.strongmemoryapi.repository.user;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.domain.entity.user.role.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndRole(String email, UserRoleEntity role);

    Optional<UserEntity> findByIdAndRole(Long id, UserRoleEntity role);

    boolean existsByUsernameOrEmail(String username, String email);

}
