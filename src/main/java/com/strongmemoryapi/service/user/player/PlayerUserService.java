package com.strongmemoryapi.service.user.player;

import com.strongmemoryapi.domain.entity.user.role.UserRoles;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.service.scorerecord.ScoreRecordService;
import com.strongmemoryapi.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerUserService extends UserService {

    @Autowired
    private ScoreRecordService scoreService;

    @Override
    @Transactional
    public UserResponse register(UserRequest request){
        UserEntity createdUser = register(request, UserRoles.ROLE_PLAYER);
        scoreService.registerInitialUserScores(createdUser);

        return parseToUserResponse(createdUser);
    }

    @Override
    public AuthResponse auth(AuthRequest request) {
        return auth(request, UserRoles.ROLE_PLAYER);
    }

    @Override
    public void updatePassword(Long id, UserPasswordUpdateRequest request) {
        updatePassword(id, request, UserRoles.ROLE_PLAYER);
    }

}
