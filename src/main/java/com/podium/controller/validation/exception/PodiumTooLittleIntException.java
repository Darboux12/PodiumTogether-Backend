package com.podium.controller.validation.exception;

public class PodiumTooLittleIntException extends RuntimeException {

    public PodiumTooLittleIntException(String valueName, int min) {
        super(valueName + " cannot be smaller than" + min + "!");
    }
}
