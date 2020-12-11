package com.podium.controller.validation.exception;

public class PodiumTimeFormatException extends RuntimeException {

    public PodiumTimeFormatException(String valueName) {
        super(valueName + " is not valid time format!");
    }
}
