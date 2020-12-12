package com.podium.dal.files.exception;

public class PodiumFileNotExistException extends RuntimeException {

    public PodiumFileNotExistException() {
        super("File with given name does not exist!");
    }
}
