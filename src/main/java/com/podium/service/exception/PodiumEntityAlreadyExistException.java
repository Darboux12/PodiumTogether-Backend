package com.podium.service.exception;

public class PodiumEntityAlreadyExistException extends Exception {

    public PodiumEntityAlreadyExistException(String entityName) {
        super(entityName + "for given data already exist!");
    }
}
