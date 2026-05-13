package com.strongmemoryapi.domain.enums;

public enum UserRole {
    ROLE_PLAYER("ROLE_PLAYER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    UserRole(String rolePlayer) {
        role = rolePlayer;
    }

}
