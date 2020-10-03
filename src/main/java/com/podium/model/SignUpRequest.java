package com.podium.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SignUpRequest {

    private String username;
    private String email;
    private String password;
    private String country;
    private Date birthday;


}
