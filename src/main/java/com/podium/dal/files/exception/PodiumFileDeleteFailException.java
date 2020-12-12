package com.podium.dal.files.exception;

public class PodiumFileDeleteFailException extends RuntimeException {

    public PodiumFileDeleteFailException(){
        super("Error occurred during deleting given file!");
    }
}
