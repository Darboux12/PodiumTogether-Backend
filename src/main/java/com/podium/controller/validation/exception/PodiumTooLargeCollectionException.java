package com.podium.controller.validation.exception;

public class PodiumTooLargeCollectionException extends RuntimeException {

    public PodiumTooLargeCollectionException(String valueName, int max) {
        super(valueName + " cannot be longer than " + max + "!");
    }
}
