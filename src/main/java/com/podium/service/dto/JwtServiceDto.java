package com.podium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtServiceDto {
    private String username;
    private String password;
}