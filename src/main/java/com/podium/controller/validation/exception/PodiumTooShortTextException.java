package com.podium.controller.validation.exception;

public class PodiumTooShortTextException extends RuntimeException {

    public PodiumTooShortTextException(String valueName, int min) {
        super(valueName + "cannot be longer than " + min + "!");
    }

}
