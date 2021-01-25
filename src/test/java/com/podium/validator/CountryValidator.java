package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.CountryAddControllerRequest;
import com.podium.controller.dto.response.CountryControllerResponse;
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

    public List<CountryControllerResponse> findAll(HttpStatus status){

        CountryControllerResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllCountry)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) CountryControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public boolean existCountryByName(String countryName,HttpStatus status){

        return

        given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("name",countryName)
                .when().get(PodiumPath.server + PodiumEndpoint.existCountryByName)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(boolean.class);



    }

    public CountryControllerResponse findCountryByName(String countryName,HttpStatus status){

        return

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .pathParam("name",countryName)
                        .when().get(PodiumPath.server + PodiumEndpoint.findCountryByName)
                        .then().assertThat()
                        .statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(CountryControllerResponse.class);

    }

}
