package com.podium.model.dto.validation.exception;

public class PodiumInvalidEmailException extends RuntimeException {

    public PodiumInvalidEmailException(String valueName) {
        super(valueName + " is invalid email address!");
    }
}
