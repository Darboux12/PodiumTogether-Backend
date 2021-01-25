package com.podium.service;

import com.podium.dal.entity.User;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private RoleService roleService;

    public SecurityService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void validateUserAdminAuthority(User user) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        if(!this.isUserAdmin(user))
            throw new PodiumAuthorityException("admin");
    }

    public void validateUserSubscriberAuthority(User user) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        if(!this.isUserSubscriber(user))
            throw new PodiumAuthorityException("subscriber");
    }

    public void validateUserAdminOrModeratorAuthority(User user) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        if(!this.isUserAdminOrModerator(user))
            throw new PodiumAuthorityException("subscriber");
    }

    public void validateUserModeratorAuthority(User user) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        if(!this.isUserModerator(user))
            throw new PodiumAuthorityException("moderator");
    }

    public boolean isUserAdmin(User user) throws PodiumEntityNotFoundException {

        return user
                .getRoles()
                .contains(this.roleService.findRoleByName("admin"));

    }

    public boolean isUserModerator(User user) throws PodiumEntityNotFoundException {

        return user
                .getRoles()
                .contains(this.roleService.findRoleByName("moderator"));

    }

    public boolean isUserSubscriber(User user) throws PodiumEntityNotFoundException {

        return user
                .getRoles()
                .contains(this.roleService.findRoleByName("subscriber"));

    }

    public boolean isUserAdminOrModerator(User user) throws PodiumEntityNotFoundException {

        return user.getRoles().contains(this.roleService.findRoleByName("admin")) ||
                user.getRoles().contains(this.roleService.findRoleByName("moderator"));

    }

}
