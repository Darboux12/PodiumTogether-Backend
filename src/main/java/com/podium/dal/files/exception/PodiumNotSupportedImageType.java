package com.podium.dal.files.exception;

public class PodiumNotSupportedImageType extends RuntimeException {

    public PodiumNotSupportedImageType(){
        super("Given image type is not supported or it is not image type!");
    }

}
