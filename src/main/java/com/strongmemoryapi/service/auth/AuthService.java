package com.strongmemoryapi.service.auth;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.domain.exception.local.InvalidCredentialsException;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.security.jwt.JwtUtils;
import com.strongmemoryapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtils jwt;

    @Autowired
    private PasswordEncoder encoder;

    public AuthResponse login(UserRole role, AuthRequest request){
        UserModel user = service.findByEmailAndRole(role, request.email());

        if(!encoder.matches(request.password(), user.getPassword())){
            throw new InvalidCredentialsException();
        }

        String token = jwt.generateToken(user);
        return new AuthResponse(token, user.getRoleString());
    }

}
