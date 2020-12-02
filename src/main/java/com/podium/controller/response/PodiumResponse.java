package com.podium.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class PodiumResponse {

    private String title;
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

}
