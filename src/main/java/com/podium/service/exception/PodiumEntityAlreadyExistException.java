package com.podium.service.exception;

public class PodiumEntityAlreadyExistException extends RuntimeException {

    public PodiumEntityAlreadyExistException(String entityName) {
        super(entityName + "for given data already exist!");
    }
}
