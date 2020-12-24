package com.podium.dal.file.exception;

public class PodiumNotSupportedDocumentType extends RuntimeException {

    public PodiumNotSupportedDocumentType(){
        super("Given document type is not supported or it is not document type!");
    }

}
