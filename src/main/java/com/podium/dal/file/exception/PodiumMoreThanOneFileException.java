package com.podium.dal.file.exception;

public class PodiumMoreThanOneFileException extends RuntimeException {

    public PodiumMoreThanOneFileException() {
        super("More than one file with given name exist!");
    }
}
