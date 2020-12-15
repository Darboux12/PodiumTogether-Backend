package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.PlaceAddRequest;
import com.podium.specification.TestSpecification;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)

                .multiPart(new MultiPartSpecBuilder(requestDto, ObjectMapperType.JACKSON_2)
                        .fileName("request.json")
                        .controlName("place")
                        .mimeType("application/vnd.custom+json").build())

                .multiPart(new MultiPartSpecBuilder("Test-Content-In-File".getBytes()).
                        fileName("image.jpg").
                        controlName("images").
                        mimeType("image/jpeg").
                        build())

                .when().post(PodiumPath.server + PodiumEndpoint.addPlace)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
