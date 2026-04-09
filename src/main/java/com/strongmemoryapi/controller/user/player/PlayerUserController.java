package com.strongmemoryapi.controller.user.player;

import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.ApiResponse;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.service.user.player.PlayerAbstractUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/player", produces = "application/json;charset=UTF-8")
public class PlayerUserController {

    @Autowired
    private PlayerAbstractUserService service;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    ApiResponse<UserResponse> register (@Valid @RequestBody UserRequest request){
        UserResponse res = service.register(request);
        return new ApiResponse<>(201, "Jogador cadastrado com sucesso.", res);
    }

    @PutMapping("/update-password/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody UserPasswordUpdateRequest request
    ){
        service.updatePassword(id, request);
    }

    @PostMapping("/auth")
    @ResponseStatus(value = HttpStatus.OK)
    ApiResponse<AuthResponse> login(@Valid @RequestBody AuthRequest request){
        AuthResponse res = service.auth(request);
        return new ApiResponse<>(200, "Autenticação de jogador realizada com sucesso.", res);
    }

}
