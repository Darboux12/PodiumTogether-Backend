package com.podium.model.request;

import com.podium.validation.annotation.PodiumTextNotEmpty;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    @PodiumTextNotEmpty
    private String username;
    @PodiumTextNotEmpty
    private String password;


    public JwtRequest() {

    }

    public JwtRequest(String username, String password) {
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