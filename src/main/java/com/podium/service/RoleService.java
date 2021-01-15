package com.podium.service;

import com.podium.constant.PodiumLimits;
import com.podium.dal.entity.Role;
import com.podium.dal.repository.RoleRepository;
import com.podium.service.exception.PodiumEntityNotFoundException;
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

    public Role findRoleByName(String roleName) throws PodiumEntityNotFoundException {
        return this.roleRepository
                .findByRole(roleName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Role"));
    }

    public Role getEntity(String roleName) throws PodiumEntityNotFoundException {

        return this.roleRepository
                .findByRole(roleName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Role"));
    }

    public Role getDefaultRole() throws PodiumEntityNotFoundException {

        return this.roleRepository
                .findByRole(PodiumLimits.defaultUserRole)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Role"));

    }
}
