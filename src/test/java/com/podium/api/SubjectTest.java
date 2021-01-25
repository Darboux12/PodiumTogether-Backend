package com.podium.api;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.response.SubjectControllerResponse;
import com.podium.controller.dto.request.SubjectAddControllerRequest;
import com.podium.specification.TestSpecification;
import com.podium.constant.PodiumLimits;
import com.podium.validator.SubjectValidator;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;


import java.text.ParseException;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class SubjectTest {

    private static String adminToken = "";
    private static String subscriberToken = "";

    private static SubjectAddControllerRequest subjectAddControllerRequest =  new SubjectAddControllerRequest("TestSubject");;

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    @Test
    void T01_Sign_In_As_Admin_User_Get_Token(){

        adminToken =

                UserValidator
                        .getInstance()
                        .signIn(new JwtControllerRequest("admin","adminadmin"),HttpStatus.OK)
                        .getToken();

    }

    @Test
    void T02_Sign_In_As_Subscriber_User_Get_Token(){

        subscriberToken =

                UserValidator
                        .getInstance()
                        .signIn(new JwtControllerRequest("TEST USERNAME_ONE","TEST PASSWORD ONE"),HttpStatus.OK)
                        .getToken();

    }

    @Test
    void T03_Add_Valid_Subject_Should_Return_Status_OK() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectAddControllerRequest,adminToken,HttpStatus.OK);

    }

    @Test
    void T04_Add_Valid_Subject_As_SubscriberShould_Return_Status_UNAUTHORIZED() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectAddControllerRequest,subscriberToken,HttpStatus.UNAUTHORIZED);

    }

    @Test
    void T05_Add_Existing_Subject_Should_Return_Status_CONFLICT() throws ParseException {

        SubjectValidator
                .getInstance()
                .add(subjectAddControllerRequest,adminToken,HttpStatus.CONFLICT);
    }

    @Test
    void T06_Add_Subject_Empty_Subject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String valueHolder = subjectAddControllerRequest.getSubject();
        subjectAddControllerRequest.setSubject("");

        SubjectValidator
                .getInstance()
                .add(subjectAddControllerRequest,adminToken,HttpStatus.CONFLICT);

        subjectAddControllerRequest.setSubject(valueHolder);
    }

    @Test
    void T07_addSubjectToShortSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toShort = StringUtils.repeat("*", PodiumLimits.minSubjectLength - 1);

        String valueHolder = subjectAddControllerRequest.getSubject();
        subjectAddControllerRequest.setSubject(toShort);

        SubjectValidator
                .getInstance()
                .add(subjectAddControllerRequest,adminToken,HttpStatus.CONFLICT);

        subjectAddControllerRequest.setSubject(valueHolder);
    }

    @Test
    void T08_addSubjectToLongSubject_ShouldReturnStatus_CONFLICT() throws ParseException {

        String toLong = StringUtils.repeat("*", PodiumLimits.maxSubjectLength + 1);

        String valueHolder = subjectAddControllerRequest.getSubject();
        subjectAddControllerRequest.setSubject(toLong);

        SubjectValidator
                .getInstance()
                .add(subjectAddControllerRequest,adminToken,HttpStatus.CONFLICT);

        subjectAddControllerRequest.setSubject(valueHolder);
    }

    @Test
    void T09_findExistingSubject_ShouldReturnStatus_OK_Response_DTO() throws ParseException {

        SubjectControllerResponse addedSubject =

                SubjectValidator
                        .getInstance()
                        .findByName(subjectAddControllerRequest.getSubject(),HttpStatus.OK);

        Assertions.assertEquals(subjectAddControllerRequest.getSubject(),addedSubject.getSubject());

    }

    @Test
    void T10_deleteSubject_ShouldReturnStatus_200() throws ParseException {

        SubjectValidator.getInstance().deleteSubjectByName(subjectAddControllerRequest.getSubject(),adminToken,HttpStatus.OK);


    }

    @Test
    void T11_Delete_Subject_As_Subscriber__ShouldReturnStatus_UNAUTHORIZED() throws ParseException {

        SubjectValidator.getInstance().deleteSubjectByName(subjectAddControllerRequest.getSubject(),subscriberToken,HttpStatus.UNAUTHORIZED);


    }

}
