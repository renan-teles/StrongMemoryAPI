package com.strongmemoryapi.service.user;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void checkExitsById(Long id, String exceptionMessage){
        if(repository.existsById(id)) return;
        throw new ResourceNotFoundException(exceptionMessage);
    }

    public UserEntity findById(Long id, String exceptionMessage){
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(exceptionMessage));
    }

}
