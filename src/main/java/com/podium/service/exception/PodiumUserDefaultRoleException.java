package com.podium.service.exception;

public class PodiumUserDefaultRoleException extends Exception {

    public PodiumUserDefaultRoleException() {
        super("You cannot delete default role! Delete user instead.");
    }
}
