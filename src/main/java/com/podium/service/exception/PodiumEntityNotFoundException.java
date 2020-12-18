package com.podium.service.exception;

public class PodiumEntityNotFoundException extends Exception {

    public PodiumEntityNotFoundException(String entityName) {
        super("Given " + entityName + " cannot be found!");
    }
}
