package com.podium.dal.file.exception;

public class PodiumFileNotExistException extends RuntimeException {

    public PodiumFileNotExistException() {
        super("File with given name does not exist!");
    }
}
