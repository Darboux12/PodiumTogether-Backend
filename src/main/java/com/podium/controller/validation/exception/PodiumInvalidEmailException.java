package com.podium.controller.validation.exception;

public class PodiumInvalidEmailException extends RuntimeException {

    public PodiumInvalidEmailException(String valueName) {
        super(valueName + " is invalid email address!");
    }
}
