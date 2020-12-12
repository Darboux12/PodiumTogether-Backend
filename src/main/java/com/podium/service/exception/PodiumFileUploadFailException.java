package com.podium.service.exception;

public class PodiumFileUploadFailException extends RuntimeException {

    public PodiumFileUploadFailException() {
        super("Error occurred during uploading file!");
    }


}
