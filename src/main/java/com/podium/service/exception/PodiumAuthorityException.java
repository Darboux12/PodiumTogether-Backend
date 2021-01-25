package com.podium.service.exception;

public class PodiumAuthorityException extends Exception {

    public PodiumAuthorityException(String roleName) {
        super("You must have " + roleName + " to perform this action!");
    }
}
