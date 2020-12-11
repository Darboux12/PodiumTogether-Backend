package com.podium.controller.validation.exception;

public class PodiumTooLargeIntException extends RuntimeException {

    public PodiumTooLargeIntException(String valueName, int max) {
        super(valueName + " cannot be bigger than" + max + "!");
    }
}
