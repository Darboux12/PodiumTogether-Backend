package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.PlaceRequestDto;
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

    public void add(PlaceRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + PodiumEndpoint.addPlace)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
