package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.EventAddRequest;
import com.podium.specification.TestSpecification;
import com.podium.controller.dto.response.EventResponse;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EventValidator {

    private static EventValidator instance;

    private EventValidator() {}

    public static EventValidator getInstance() {
        if(instance == null) {
            instance = new EventValidator();
        }
        return instance;
    }

    public void add(EventAddRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addEvent)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<EventResponse> findAll(){

        EventResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllCity)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) EventResponse[].class);

        return Arrays.asList(dtos);

    }

    public EventResponse findByTitle(String eventTitle, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("name",eventTitle)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findCityByName)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(EventResponse.class);

    }

    public void existCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",cityName)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.existCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",cityName)
                .delete(PodiumPath.server + PodiumEndpoint.deleteCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }




}
