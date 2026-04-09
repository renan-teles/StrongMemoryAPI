package com.strongmemoryapi.enums;

public enum UserRoles {
    ROLE_ADMINISTRATOR("ROLE_ADMINISTRATOR"),
    ROLE_PLAYER("ROLE_PLAYER");

    private String role;

    UserRoles(String rolePlayer) {
        role = rolePlayer;
    }
}
