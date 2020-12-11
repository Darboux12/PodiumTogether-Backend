package com.podium.controller.validation.exception;

public class PodiumDatePastException extends RuntimeException {

    public PodiumDatePastException(String valueName) {
        super(valueName + "cannot be in the past!");
    }
}
