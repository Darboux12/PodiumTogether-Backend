package com.podium.service.exception;

public class PodiumEntityTimeConsistencyError extends Exception {

    public PodiumEntityTimeConsistencyError(String message) {
        super(message + " violate time consistency (earlier time cannot be after later time");
    }
}
