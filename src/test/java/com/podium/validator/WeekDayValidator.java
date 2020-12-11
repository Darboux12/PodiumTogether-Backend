package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.response.WeekDayResponse;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class WeekDayValidator {

    private static WeekDayValidator instance;

    private WeekDayValidator() {}

    public static WeekDayValidator getInstance() {
        if(instance == null) {
            instance = new WeekDayValidator();
        }
        return instance;
    }

    public List<WeekDayResponse> findAll(){

        WeekDayResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllWeekDay)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) WeekDayResponse[].class);

        return Arrays.asList(dtos);

    }
}
