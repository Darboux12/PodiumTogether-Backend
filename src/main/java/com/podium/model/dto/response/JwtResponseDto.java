package com.podium.model.dto.response;

import java.io.Serializable;

public class JwtResponseDto implements Serializable
{

    private final String token;

    public JwtResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }


}