package com.podium.dal.file.exception;

public class PodiumFileDeleteFailException extends RuntimeException {

    public PodiumFileDeleteFailException(){
        super("Error occurred during deleting given file!");
    }
}
