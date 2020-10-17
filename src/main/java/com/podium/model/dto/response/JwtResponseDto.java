package com.podium.model.dto.response;

import java.io.Serializable;

public class JwtResponseDto implements Serializable
{

    private final String token;

    private String username;

    public JwtResponseDto(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return username;
    }
}