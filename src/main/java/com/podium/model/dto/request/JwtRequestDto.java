package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumTextNotEmpty;

import java.io.Serializable;

public class JwtRequestDto implements Serializable {

    @PodiumTextNotEmpty
    private String username;
    @PodiumTextNotEmpty
    private String password;


    public JwtRequestDto() {

    }

    public JwtRequestDto(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}