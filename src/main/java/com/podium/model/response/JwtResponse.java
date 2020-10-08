package com.podium.model.response;

import java.io.Serializable;

public class JwtResponse implements Serializable
{

    private final String token;

    private String username;

    public JwtResponse(String token, String username) {
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