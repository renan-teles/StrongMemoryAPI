package com.strongmemoryapi.service.user.details;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String username, password;
    private final Collection<? extends GrantedAuthority> authorities;
}
