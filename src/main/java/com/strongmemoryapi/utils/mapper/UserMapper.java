package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.dto.user.UserResponse;

public class UserMapper {

    public static UserResponse toDTO(UserModel model){
        return new UserResponse(
                model.getUsername(),
                model.getEmail(),
                model.getRoleString()
        );
    }

}
