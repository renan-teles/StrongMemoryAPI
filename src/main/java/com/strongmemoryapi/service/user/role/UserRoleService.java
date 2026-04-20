package com.strongmemoryapi.service.user.role;

import com.strongmemoryapi.domain.entity.user.role.UserRoleEntity;
import com.strongmemoryapi.domain.enums.UserRoles;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.repository.user.role.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    public UserRoleEntity findAdministratorRole(){
        return this.findUserRole(UserRoles.ROLE_ADMINISTRATOR);
    }

    public UserRoleEntity findPlayerRole(){
        return this.findUserRole(UserRoles.ROLE_PLAYER);
    }

    public UserRoleEntity findUserRole(UserRoles role){
        return repository.findByRole(role.toString())
                .orElseThrow(() -> new ResourceNotFoundException(
                            "Papel do usuário não encontrado."
                        )
                );
    }

    public boolean isPlayerRole(UserRoleEntity role){
        return role.getRole().equalsIgnoreCase(UserRoles.ROLE_PLAYER.toString());
    }

}
