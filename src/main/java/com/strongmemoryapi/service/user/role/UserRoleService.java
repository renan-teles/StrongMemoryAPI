package com.strongmemoryapi.service.user.role;

import com.strongmemoryapi.domain.entity.user.role.UserRoleEntity;
import com.strongmemoryapi.enums.UserRoles;
import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.repository.user.role.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    public UserRoleEntity getAdministratorRole(){
        return this.getUserRole(UserRoles.ROLE_ADMINISTRATOR);
    }

    public UserRoleEntity getPlayerRole(){
        return this.getUserRole(UserRoles.ROLE_PLAYER);
    }

    public UserRoleEntity getUserRole(UserRoles role){
        return this.repository.findByRole(role.toString())
                .orElseThrow(() -> new ResourceNotFoundException(
                            "Papel do usuário não encontrado."
                        )
                );
    }

    public boolean isPlayerRole(UserRoleEntity role){
        return role.getRole().equalsIgnoreCase(UserRoles.ROLE_PLAYER.toString());
    }

}
