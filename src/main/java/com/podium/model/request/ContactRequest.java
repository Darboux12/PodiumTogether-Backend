package com.podium.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {

    private String userEmail;
    private String subject;
    private String message;


}
