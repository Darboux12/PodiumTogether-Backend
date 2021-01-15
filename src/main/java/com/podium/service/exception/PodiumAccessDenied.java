package com.podium.service.exception;

public class PodiumAccessDenied extends Exception {

    public PodiumAccessDenied(String roleName) {
        super("You be " + roleName + " to access this content");
    }
}
