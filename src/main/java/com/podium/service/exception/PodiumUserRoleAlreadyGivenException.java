package com.podium.service.exception;

public class PodiumUserRoleAlreadyGivenException extends Exception {

    public PodiumUserRoleAlreadyGivenException(String roleName) {
        super("Given user already own " + roleName + " role!");
    }
}
