package com.strongmemoryapi.controller;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.service.auth.AuthService;
import com.strongmemoryapi.utils.response.ResponseApi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* ENPOINTS TESTADOS */
@RestController
@RequestMapping(value = "/api/auth", produces = "application/json;charset=UTF-8")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping
    public ResponseEntity<ApiDataResponse<AuthResponse>> login(
            @Valid @RequestBody AuthRequest requestBody,
            @RequestParam UserRole role
    ){
        AuthResponse authRes = service.login(role, requestBody);
        return ResponseApi
                .okResponse(authRes, "Autenticação realizada com sucesso.");
    }

}
