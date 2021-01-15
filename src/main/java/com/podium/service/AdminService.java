package com.podium.service;

import com.podium.dal.entity.Role;
import com.podium.dal.entity.User;
import com.podium.dal.repository.UserRepository;
import com.podium.service.dto.request.UserRoleUpdateServiceRequest;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private UserRepository userRepository;

    private UserService userService;
    private RoleService roleService;
    private ResourceService resourceService;

    public AdminService(UserRepository userRepository, UserService userService, RoleService roleService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    public void grantUserRole(UserRoleUpdateServiceRequest request) throws PodiumEntityNotFoundException {
        User user = userService.findUserByUsername(request.getUsername());
        Role role = this.roleService.findRoleByName(request.getRole());
        user.getRoles().add(role);
        this.userRepository.save(user);
    }

    public void synchronizeResourcesWithSystemFiles(){
        this.resourceService.synchronizeResourcesWithSystemFiles();
    }
}
