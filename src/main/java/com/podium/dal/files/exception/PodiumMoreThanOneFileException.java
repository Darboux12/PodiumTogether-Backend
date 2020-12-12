package com.podium.dal.files.exception;

public class PodiumMoreThanOneFileException extends RuntimeException {

    public PodiumMoreThanOneFileException() {
        super("More than one file with given name exist!");
    }
}
