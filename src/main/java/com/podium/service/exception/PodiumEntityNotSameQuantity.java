package com.podium.service.exception;

public class PodiumEntityNotSameQuantity extends Exception {

    public PodiumEntityNotSameQuantity(String value, String sameAsWhat) {
        super("Number of " + value + " must be the same as number of " + sameAsWhat);
    }
}
