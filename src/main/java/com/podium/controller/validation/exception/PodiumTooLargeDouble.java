package com.podium.controller.validation.exception;

public class PodiumTooLargeDouble extends RuntimeException {

    public PodiumTooLargeDouble(String valueName, double max) {
        super(valueName + " cannot be bigger than" + max + "!");
    }
}
