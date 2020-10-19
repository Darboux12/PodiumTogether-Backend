package com.podium.validator;

import com.podium.helper.Endpoint;
import com.podium.helper.Path;
import com.podium.helper.TestSpecification;
import com.podium.model.dto.request.EventRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.EventResponseDto;
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

    public void add(EventRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addEvent)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<EventResponseDto> findAll(){

        EventResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + Endpoint.findAllCity)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type)EventResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public EventResponseDto findByTitle(String eventTitle, HttpStatus status){

        return

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("name",eventTitle)
                        .when()
                        .get(Path.server + Endpoint.findCityByName)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(EventResponseDto.class);

    }

    public void existCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .pathParam("name",cityName)
                .when()
                .get(Path.server + Endpoint.existCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void deleteCityByName(String cityName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",cityName)
                .delete(Path.server + Endpoint.deleteCityByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }




}
