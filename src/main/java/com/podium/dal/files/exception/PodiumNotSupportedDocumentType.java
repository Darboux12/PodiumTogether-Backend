package com.podium.dal.files.exception;

public class PodiumNotSupportedDocumentType extends RuntimeException {

    public PodiumNotSupportedDocumentType(){
        super("Given document type is not supported or it is not document type!");
    }

}
