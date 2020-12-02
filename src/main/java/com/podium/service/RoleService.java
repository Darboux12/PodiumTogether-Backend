package com.podium.service;

import com.podium.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository repository) {
        this.roleRepository = repository;
    }

    public boolean existRole(String role){
        return this.roleRepository.existsByRole(role);
    }

}
