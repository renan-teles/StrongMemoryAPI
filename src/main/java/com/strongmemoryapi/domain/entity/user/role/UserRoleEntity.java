package com.strongmemoryapi.domain.entity.user.role;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_roles")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserEntity> users;

    @Column(nullable = false, unique = true)
    private String role;

    public Byte getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

}
