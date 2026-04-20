package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.dto.response.UserResponse;

public class UserMapper {

    public static UserResponse toDTO(UserEntity user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRole()
        );
    }

}
