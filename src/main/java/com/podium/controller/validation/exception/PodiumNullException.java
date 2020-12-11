package com.podium.controller.validation.exception;

public class PodiumNullException extends RuntimeException {

    public PodiumNullException(String valueName) {
        super(valueName + "cannot be null!");
    }
}
