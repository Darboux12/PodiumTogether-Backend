package com.podium.service;

import com.podium.dal.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository repository) {
        this.roleRepository = repository;
    }

    public boolean existRoleByName(String roleName){
        return this.roleRepository.existsByRole(roleName);
    }

}
