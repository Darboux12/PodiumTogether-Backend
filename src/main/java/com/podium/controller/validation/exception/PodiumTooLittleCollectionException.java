package com.podium.controller.validation.exception;

public class PodiumTooLittleCollectionException extends RuntimeException {

    public PodiumTooLittleCollectionException(String valueName, int min) {
        super(valueName + "cannot be longer than " + min + "!");
    }

}
