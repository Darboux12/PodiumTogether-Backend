package com.podium.controller.validation.exception;

public class PodiumTooLittleDouble extends  RuntimeException {

    public PodiumTooLittleDouble(String valueName, double min) {
        super(valueName + "cannot be smaller than " + min + "!");
    }
}
