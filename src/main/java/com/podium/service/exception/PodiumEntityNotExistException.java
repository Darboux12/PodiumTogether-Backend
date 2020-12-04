package com.podium.service.exception;

public class PodiumEntityNotExistException extends RuntimeException {

    public PodiumEntityNotExistException(String entityName) {
        super("Given " + entityName + "entity does not exist!");
    }
}
