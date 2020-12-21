package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.ContactAddControllerRequest;
import com.podium.controller.dto.response.ContactControllerResponse;
import com.podium.specification.TestSpecification;
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

    public void add(ContactAddControllerRequest requestDto, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post(PodiumPath.server + PodiumEndpoint.addContact)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public List<ContactControllerResponse> findAll(){

        ContactControllerResponse[] dtos =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .when().get(PodiumPath.server + PodiumEndpoint.findAllContact)
                        .then().assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public List<ContactControllerResponse> findByEmail(String email, HttpStatus status){

        ContactControllerResponse[] dtos =

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("email",email)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findAllContactByEmail)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public List<ContactControllerResponse> findBySubject(String subject, HttpStatus status){

        ContactControllerResponse[] dtos =

                given().spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .pathParam("subject",subject)
                        .when()
                        .get(PodiumPath.server + PodiumEndpoint.findAllContactBySubject)
                        .then().assertThat().statusCode(status.value())
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as((Type) ContactControllerResponse[].class);

        return Arrays.asList(dtos);

    }

    public void deleteContactById(int id, HttpStatus status){

        given().spec(TestSpecification.buildRequestSpec())
                .when()
                .pathParam("id",id)
                .delete(PodiumPath.server + PodiumEndpoint.deleteContact)
                .then().assertThat().statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }











}
