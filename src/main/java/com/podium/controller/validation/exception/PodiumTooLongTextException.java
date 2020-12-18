package com.podium.controller.validation.exception;

public class PodiumTooLongTextException extends RuntimeException {

    public PodiumTooLongTextException(String valueName, int max) {
        super(valueName + " cannot be longer than " + max + "!");
    }

}
