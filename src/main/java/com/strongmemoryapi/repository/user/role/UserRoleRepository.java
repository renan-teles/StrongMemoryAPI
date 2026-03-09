package com.strongmemoryapi.repository.user.role;

import com.strongmemoryapi.domain.entity.user.role.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Byte> {

    Optional<UserRoleEntity> findByRole(String role);

}
