package com.podium.model.dto.validation.exception;

public class PodiumTooLittleIntException extends RuntimeException {

    public PodiumTooLittleIntException(String valueName, int min) {
        super(valueName + " cannot be smaller than" + min + "!");
    }
}
