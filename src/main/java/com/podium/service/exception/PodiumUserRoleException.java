package com.podium.service.exception;

public class PodiumUserRoleException extends Exception {

    public PodiumUserRoleException(String roleName) {
        super("User does not own " + roleName + "role");
    }
}
