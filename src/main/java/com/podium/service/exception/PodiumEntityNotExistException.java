package com.podium.service.exception;

public class PodiumEntityNotExistException extends Exception {

    public PodiumEntityNotExistException(String entityName) {
        super("Given " + entityName + "entity does not exist!");
    }
}
