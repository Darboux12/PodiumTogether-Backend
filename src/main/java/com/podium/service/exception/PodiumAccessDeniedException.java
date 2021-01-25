package com.podium.service.exception;

public class PodiumAccessDeniedException extends Exception {

    public PodiumAccessDeniedException(String roleName) {
        super("You be " + roleName + " to access this content");
    }
}
