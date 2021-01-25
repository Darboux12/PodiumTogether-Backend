package com.podium.service.exception;

public class PodiumSelfDegradationException extends Exception {

    public PodiumSelfDegradationException(String roleName) {
        super("You cannot take away your own" + roleName + " role!");
    }
}
