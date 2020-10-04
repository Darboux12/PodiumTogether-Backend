package com.podium.helper;

import com.podium.model.User;

import java.util.Date;

public class Constant {

    public static User user;

    public Constant() {
        user.setUsername("testUsername");
        user.setBirthday(new Date());
    }
}
