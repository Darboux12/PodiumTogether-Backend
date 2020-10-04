package com.podium.api;

import com.podium.helper.Path;
import com.podium.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

class UserTest {

    private User validUser;

    @BeforeClass
    public void beforeClass(){

        validUser = new User();
        validUser.setUsername("test_username");



    }


    @Test
    void name() {







    }


}
