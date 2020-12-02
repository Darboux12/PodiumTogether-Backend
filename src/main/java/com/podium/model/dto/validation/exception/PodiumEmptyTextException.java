package com.podium.model.dto.validation.exception;

public class PodiumEmptyTextException extends RuntimeException {

    public PodiumEmptyTextException(String valueName) {
        super(valueName + "cannot be empty!");
    }
}
