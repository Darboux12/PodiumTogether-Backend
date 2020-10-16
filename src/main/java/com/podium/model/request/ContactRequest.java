package com.podium.model.request;

import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.validation.annotation.PodiumValidEmail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequest {

    @PodiumValidEmail
    private String userEmail;

    @PodiumTextNotEmpty
    private String subject;
    private String message;


}
