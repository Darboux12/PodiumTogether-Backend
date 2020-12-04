package com.podium.service.exception;

public class PodiumEntityNotFoundException extends RuntimeException {

    public PodiumEntityNotFoundException(String entityName) {
        super("Given " + entityName + "cannot be found!");
    }
}
