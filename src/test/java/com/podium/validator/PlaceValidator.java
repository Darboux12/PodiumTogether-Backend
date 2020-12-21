package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.PlaceAddControllerRequest;
import com.podium.controller.dto.response.PlaceControllerResponse;
import com.podium.specification.TestSpecification;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.lang.reflect.Type;

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

    public void add(PlaceAddControllerRequest requestDto, HttpStatus status){

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

                .multiPart(new MultiPartSpecBuilder("Test-Content-In-File".getBytes()).
                        fileName("document.pdf").
                        controlName("documents").
                        mimeType("application/pdf").
                        build())

                .when().post(PodiumPath.server + PodiumEndpoint.addPlace)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deletePlaceById(int id, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when().delete(PodiumPath.server + PodiumEndpoint.deletePlaceById)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    public PlaceControllerResponse findByName(String name, HttpStatus status){

        if(status == HttpStatus.OK)

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("name",name)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findPlaceByName)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) PlaceControllerResponse.class);

        else

            given().spec(TestSpecification.buildRequestSpec())
                    .contentType(ContentType.JSON)
                    .pathParam("name",name)
                    .when()
                    .get(PodiumPath.server + PodiumEndpoint.findPlaceByName)
                    .then().assertThat().statusCode(status.value())
                    .spec(TestSpecification.buildResponseSpec());


        return null;

    }

}
