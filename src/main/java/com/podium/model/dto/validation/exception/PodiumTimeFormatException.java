package com.podium.model.dto.validation.exception;

public class PodiumTimeFormatException extends RuntimeException {

    public PodiumTimeFormatException(String valueName) {
        super(valueName + " is not valid time format!");
    }
}
