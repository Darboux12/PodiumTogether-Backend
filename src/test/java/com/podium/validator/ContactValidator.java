package com.podium.validator;

import com.podium.helper.Endpoint;
import com.podium.helper.Path;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.specification.TestSpecification;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ContactValidator {

    private static ContactValidator instance;

    private ContactValidator() {}

    public static ContactValidator getInstance() {
        if(instance == null) {
            instance = new ContactValidator();
        }
        return instance;
    }

    public void add(ContactRequestDto requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(Path.server + Endpoint.addContact)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<ContactResponseDto> findAll(){

        ContactResponseDto[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(Path.server + Endpoint.findAllContact)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type)ContactResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public List<ContactResponseDto> findByEmail(String email, HttpStatus status){

        ContactResponseDto[] dtos =

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("email",email)
                        .when()
                        .get(Path.server + Endpoint.findAllContactByEmail)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactResponseDto[].class);

        return Arrays.asList(dtos);

    }

    public List<ContactResponseDto> findBySubject(String subject, HttpStatus status){

        ContactResponseDto[] dtos =

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("subject",subject)
                        .when()
                        .get(Path.server + Endpoint.findAllContactBySubject)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactResponseDto[].class);

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

    public void deleteContactById(int id, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("id",id)
                .delete(Path.server + Endpoint.deleteContact)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }











}
