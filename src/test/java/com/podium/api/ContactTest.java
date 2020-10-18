package com.podium.api;

import com.podium.helper.*;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.validation.validators.PodiumLimits;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;


import java.text.ParseException;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContactTest {

    private static ContactRequestDto contactRequestDTO;
    private static Subject addedSubject;
    private static SubjectRequestDto subjectRequestDto;

    @BeforeClass
    public static void beforeClass(){

        TestLogger.setUp();
        contactRequestDTO = Constant.getValidContactRequestDto();
        subjectRequestDto = Constant.getValidSubjectRequestDto();
    }

    @Test
    public void T01_addValidSubject_ShouldReturnStatus_OK() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(subjectRequestDto)
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T02_addExistingSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(subjectRequestDto)
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T03_addSubjectEmptySubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String valueHolder = subjectRequestDto.getSubject();
        subjectRequestDto.setSubject("");

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(subjectRequestDto)
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        subjectRequestDto.setSubject(valueHolder);
    }

       @Test
    public void T04_addSubjectToShortSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minSubjectLength - 1);

        String valueHolder = subjectRequestDto.getSubject();
        subjectRequestDto.setSubject(toShort);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(subjectRequestDto)
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        subjectRequestDto.setSubject(valueHolder);
    }

    @Test
    public void T05_addSubjectToLongSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxSubjectLength + 1);

        String valueHolder = subjectRequestDto.getSubject();
        subjectRequestDto.setSubject(toLong);

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(subjectRequestDto)
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(HttpStatus.CONFLICT.value())
                .spec(TestSpecification.buildResponseSpec());

        subjectRequestDto.setSubject(valueHolder);
    }

    @Test
    public void T06_findExistingSubject_ShouldReturnStatus_OK() throws ParseException {

        addedSubject =

        given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("name","TestSubject")
                .when()
                .get(Path.server + Endpoint.findSubjectByName)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(Subject.class);

        contactRequestDTO.setSubject(addedSubject.getName());

    }

    @Test
    public void T07_addValidContact_ShouldReturnStatus_OK() throws ParseException {

        contactRequestDTO.setSubject(subjectRequestDto.getSubject());

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(contactRequestDTO)
                .when()
                .post(Path.server + Endpoint.addContact)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T06_findValidContact_ShouldReturnStatus_OK() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .queryParam("userEmail", contactRequestDTO.getUserEmail())
                .queryParam("subject", contactRequestDTO.getSubject())
                .queryParam("message", contactRequestDTO.getMessage())
                .when()
                .get(Path.server + Endpoint.findContact)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T07_findValidContact_ShouldReturnEntity_Contact() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .queryParam("userEmail", contactRequestDTO.getUserEmail())
                .queryParam("subject", contactRequestDTO.getSubject())
                .queryParam("message", contactRequestDTO.getMessage())
                .when()
                .get(Path.server + Endpoint.findContact)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(Contact.class);
    }

    @Test
    public void T08_deleteValidContact_ShouldReturnStatus_200() throws ParseException {

        Contact contact =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .queryParam("userEmail", contactRequestDTO.getUserEmail())
                        .queryParam("subject", contactRequestDTO.getSubject())
                        .queryParam("message", contactRequestDTO.getMessage())
                        .when()
                        .get(Path.server + Endpoint.findContact)
                        .then().assertThat()
                        .statusCode(200)
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(Contact.class);

        int id = contact.getContactId();

        given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("id",id)
                .when()
                .delete(Path.server + Endpoint.deleteContact)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());



    }

    @Test
    public void T09_deleteSubject_ShouldReturnStatus_200() throws ParseException {

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .pathParam("name","TestSubject")
                        .when()
                        .delete(Path.server + Endpoint.deleteSubject)
                        .then().assertThat()
                        .statusCode(200)
                        .spec(TestSpecification.buildResponseSpec());


    }



}
