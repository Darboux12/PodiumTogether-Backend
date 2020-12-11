package com.podium.api;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.response.SubjectResponse;
import com.podium.controller.dto.request.SubjectAddRequest;
import com.podium.specification.TestSpecification;
import com.podium.constant.PodiumLimits;
import com.podium.validator.SubjectValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;


import java.text.ParseException;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class SubjectTest {

    private static SubjectAddRequest subjectAddRequest;

    @BeforeAll
    static void beforeClass(){

        TestLogger.setUp();
        subjectAddRequest = new SubjectAddRequest("TestSubject");
    }

    @Test
    void T01_addValidSubject_ShouldReturnStatus_OK() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectAddRequest,HttpStatus.OK);

    }

    @Test
    void T02_addExistingSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectAddRequest,HttpStatus.CONFLICT);
    }

    @Test
    void T03_addSubjectEmptySubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String valueHolder = subjectAddRequest.getSubject();
        subjectAddRequest.setSubject("");

        SubjectValidator
                .getInstance()
                .add(subjectAddRequest,HttpStatus.CONFLICT);

        subjectAddRequest.setSubject(valueHolder);
    }

    @Test
    void T04_addSubjectToShortSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minSubjectLength - 1);

        String valueHolder = subjectAddRequest.getSubject();
        subjectAddRequest.setSubject(toShort);

        SubjectValidator
                .getInstance()
                .add(subjectAddRequest,HttpStatus.CONFLICT);

        subjectAddRequest.setSubject(valueHolder);
    }

    @Test
    void T05_addSubjectToLongSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxSubjectLength + 1);

        String valueHolder = subjectAddRequest.getSubject();
        subjectAddRequest.setSubject(toLong);

        SubjectValidator
                .getInstance()
                .add(subjectAddRequest,HttpStatus.CONFLICT);

        subjectAddRequest.setSubject(valueHolder);
    }

    @Test
    void T06_findExistingSubject_ShouldReturnStatus_OK_Response_DTO() throws ParseException {

        SubjectResponse addedSubject =

                SubjectValidator
                        .getInstance()
                        .findByName(subjectAddRequest.getSubject(),HttpStatus.OK);

        Assertions.assertEquals(subjectAddRequest.getSubject(),addedSubject.getSubject());

    }

    @Test
    void T07_deleteSubject_ShouldReturnStatus_200() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("name","TestSubject")
                .when()
                .delete(PodiumPath.server + PodiumEndpoint.deleteSubject)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());


    }

}
