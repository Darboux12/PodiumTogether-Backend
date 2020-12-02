package com.podium.model.dto.validation.exception;

public class PodiumTooLargeCollectionException extends RuntimeException {

    public PodiumTooLargeCollectionException(String valueName, int max) {
        super(valueName + "cannot be longer than " + max + "!");
    }
}
