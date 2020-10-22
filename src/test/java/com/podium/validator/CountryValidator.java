package com.podium.validator;

import com.podium.helper.Endpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CountryValidator {

    private static  CountryValidator instance;

    private  CountryValidator() {}

    public static  CountryValidator getInstance() {
        if(instance == null) {
            instance = new  CountryValidator();
        }
        return instance;
    }

    public void add(CountryRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addCountry)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<CountryResponseDto> findAll(){

        CountryResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + Endpoint.findAllCountry)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type)CountryResponseDto[].class);

        return Arrays.asList(dtos);

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

    public void delete(String countryName, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("name",countryName)
                .delete(Path.server + Endpoint.deleteCountryByName)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());;

    }






}
