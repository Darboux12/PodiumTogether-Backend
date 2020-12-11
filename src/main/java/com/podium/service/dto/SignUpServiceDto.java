package com.podium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class SignUpServiceDto {
    private String username;
    private String email;
    private String password;
    private String country;
    private Date birthday;
}
