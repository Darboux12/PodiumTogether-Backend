package com.podium.controller.validation.exception;

public class PodiumEmptyTextException extends RuntimeException {

    public PodiumEmptyTextException(String valueName) {
        super(valueName + " cannot be empty!");
    }
}
