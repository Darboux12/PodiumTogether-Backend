package com.podium.api;

import com.podium.helper.*;
import com.podium.model.entity.News;
import com.podium.model.entity.User;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewsTest {

    @BeforeClass
    public static void  beforeClass(){

            TestLogger.setUp();
    }

    @Test
    public void T01_getAllNews_ShouldReturnStatus_200(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + Endpoint.findAllNews)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());

    }

    @Test
    public void T02_getAllNews_ShouldReturnIterable_News(){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when()
                .get(Path.server + Endpoint.findAllNews)
                .then().assertThat().statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(News[].class);
    }

}
