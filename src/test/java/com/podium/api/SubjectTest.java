package com.podium.api;

import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.SubjectResponseDto;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.specification.TestSpecification;
import com.podium.validation.validators.PodiumLimits;
import com.podium.validator.SubjectValidator;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
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
public class SubjectTest {

    private static SubjectRequestDto subjectRequestDto;

    @BeforeClass
    public static void beforeClass(){

        TestLogger.setUp();
        subjectRequestDto = Constant.getValidSubjectRequestDto();
    }

    @Test
    public void T01_addValidSubject_ShouldReturnStatus_OK() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectRequestDto,HttpStatus.OK);

    }

    @Test
    public void T02_addExistingSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectRequestDto,HttpStatus.CONFLICT);
    }

    @Test
    public void T03_addSubjectEmptySubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String valueHolder = subjectRequestDto.getSubject();
        subjectRequestDto.setSubject("");

        SubjectValidator
                .getInstance()
                .add(subjectRequestDto,HttpStatus.CONFLICT);

        subjectRequestDto.setSubject(valueHolder);
    }

    @Test
    public void T04_addSubjectToShortSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minSubjectLength - 1);

        String valueHolder = subjectRequestDto.getSubject();
        subjectRequestDto.setSubject(toShort);

        SubjectValidator
                .getInstance()
                .add(subjectRequestDto,HttpStatus.CONFLICT);

        subjectRequestDto.setSubject(valueHolder);
    }

    @Test
    public void T05_addSubjectToLongSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxSubjectLength + 1);

        String valueHolder = subjectRequestDto.getSubject();
        subjectRequestDto.setSubject(toLong);

        SubjectValidator
                .getInstance()
                .add(subjectRequestDto,HttpStatus.CONFLICT);

        subjectRequestDto.setSubject(valueHolder);
    }

    @Test
    public void T06_findExistingSubject_ShouldReturnStatus_OK_Response_DTO() throws ParseException {

        SubjectResponseDto addedSubject =

                SubjectValidator
                        .getInstance()
                        .findByName(subjectRequestDto.getSubject(),HttpStatus.OK);

        Assert.assertEquals(subjectRequestDto.getSubject(),addedSubject.getSubject());

    }

    @Test
    public void T07_deleteSubject_ShouldReturnStatus_200() throws ParseException {

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
