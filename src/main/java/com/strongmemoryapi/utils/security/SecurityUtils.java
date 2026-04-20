package com.strongmemoryapi.utils.security;

import com.strongmemoryapi.exception.local.UnauthorizedException;
import com.strongmemoryapi.service.user.details.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("Usuário não autenticado.");
        }

        Object principal = auth.getPrincipal();

        if (!(principal instanceof UserDetailsImpl user)) {
            throw new UnauthorizedException("Usuário inválido.");
        }

        return user.getId();
    }

}
