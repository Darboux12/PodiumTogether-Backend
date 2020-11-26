package com.podium.constant;

import org.springframework.http.HttpStatus;

public class PodiumStatus {

    public static final HttpStatus requestBodyValidationConflict = HttpStatus.CONFLICT;

    public static final HttpStatus requestBodyDataNotFound = HttpStatus.NOT_FOUND;

    public static final HttpStatus requestSuccess = HttpStatus.OK;
}
