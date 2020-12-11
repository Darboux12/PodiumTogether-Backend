package com.podium.controller.validation.exception;

public class PodiumImageTypeException extends RuntimeException {

    public PodiumImageTypeException(String valueName) {
        super(valueName + "is not supported image type!");
    }
}
