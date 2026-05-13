package com.strongmemoryapi.controller.user;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.dto.user.UpdatePasswordRequest;
import com.strongmemoryapi.dto.user.RegisterUserRequest;
import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.dto.user.UserResponse;
import com.strongmemoryapi.service.user.UserService;
import com.strongmemoryapi.utils.mapper.UserMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import com.strongmemoryapi.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
     value = "/api/player",
     produces = "application/json;charset=UTF-8"
)
public class PlayerController {

    private final UserRole ROLE = UserRole.ROLE_PLAYER;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserResponse>> register (
            @Valid @RequestBody RegisterUserRequest requestBody
    ){
        UserResponse registeredUser = UserMapper.toDTO(userService.register(ROLE, requestBody));
        return ResponseApi
                .createdResponse(registeredUser, "Jogador cadastrado com sucesso.");
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updatePassword(userId, ROLE, requestBody);
        return ResponseApi.noContentResponse();
    }

}
