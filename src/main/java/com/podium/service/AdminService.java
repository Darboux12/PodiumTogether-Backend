package com.podium.service;

import com.podium.dal.entity.Role;
import com.podium.dal.entity.User;
import com.podium.dal.repository.UserRepository;
import com.podium.service.dto.request.UserRoleUpdateServiceRequest;
import com.podium.service.exception.*;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private UserRepository userRepository;

    private UserService userService;
    private RoleService roleService;
    private ResourceService resourceService;
    private SecurityService securityService;

    public AdminService(UserRepository userRepository, UserService userService, RoleService roleService, ResourceService resourceService, SecurityService securityService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
        this.securityService = securityService;
    }

    public void grantUserRole(UserRoleUpdateServiceRequest request) throws PodiumEntityNotFoundException, PodiumAuthorityException, PodiumUserRoleAlreadyGivenException, PodiumSelfPromotionError {

        User admin = userService.getEntity(request.getAdminUserName());
        this.securityService.validateUserAdminAuthority(admin);

        User user = userService.getEntity(request.getUsername());
        Role role = this.roleService.findRoleByName(request.getRole());

        if(this.isRoleAlreadyGiven(user,role))
            throw new PodiumUserRoleAlreadyGivenException(role.getRole());

        if(request.getUsername().equals(request.getAdminUserName()))
            throw new PodiumSelfPromotionError("admin");

        user.getRoles().add(role);

        this.userRepository.save(user);
    }

    public void degradeUserRole(UserRoleUpdateServiceRequest request) throws PodiumEntityNotFoundException, PodiumUserRoleException, PodiumUserDefaultRoleException, PodiumAuthorityException, PodiumSelfDegradationException {

        User admin = userService.getEntity(request.getAdminUserName());
        this.securityService.validateUserAdminAuthority(admin);

        User user = userService.findUserByUsername(request.getUsername(),request.getAdminUserName());
        Role role = this.roleService.findRoleByName(request.getRole());

        if(!user.getRoles().contains(role))
            throw new PodiumUserRoleException(role.getRole());

        if(this.roleService.getDefaultRole().equals(role))
            throw new PodiumUserDefaultRoleException();

        if(request.getUsername().equals(request.getAdminUserName()))
            throw new PodiumSelfDegradationException("admin");

        user.getRoles().remove(role);

        this.userRepository.save(user);
    }

    public void synchronizeResourcesWithSystemFiles(String adminUsername) throws PodiumAuthorityException, PodiumEntityNotFoundException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        this.resourceService.synchronizeResourcesWithSystemFiles();
    }

    private boolean isRoleAlreadyGiven(User user,Role role){
        return user.getRoles().contains(role);
    }
}
