package com.podium.model.dto.validation.exception;

public class PodiumNullException extends RuntimeException {

    public PodiumNullException(String valueName) {
        super(valueName + "cannot be null!");
    }
}
