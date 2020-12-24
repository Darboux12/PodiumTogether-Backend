package com.podium.dal.file.exception;

public class PodiumNotSupportedImageType extends RuntimeException {

    public PodiumNotSupportedImageType(){
        super("Given image type is not supported or it is not image type!");
    }

}
