package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.PlaceAddRequest;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class PlaceValidator {

    private static PlaceValidator instance;

    private PlaceValidator() {}

    public static PlaceValidator getInstance() {
        if(instance == null) {
            instance = new PlaceValidator();
        }
        return instance;
    }

    public void add(PlaceAddRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addPlace)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
