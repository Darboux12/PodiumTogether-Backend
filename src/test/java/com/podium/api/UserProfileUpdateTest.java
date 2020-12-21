package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.ProfileUpdateControllerRequest;
import com.podium.controller.dto.request.SignUpControllerRequest;
import com.podium.controller.dto.response.UserControllerResponse;
import com.podium.validator.UserValidator;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class UserProfileUpdateTest {

    private static SignUpControllerRequest signUpControllerRequestOne;
    private static ProfileUpdateControllerRequest updateRequestDto;

    @BeforeAll
    static void beforeClass() throws ParseException {
        TestLogger.setUp();
        signUpControllerRequestOne = new SignUpControllerRequest(
                "TEST USERNAME_ONE",
                "TEST_MAIL_ONE@gmail.com",
                "TEST PASSWORD ONE",
                "POLAND",
                new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-13")
        );

        updateRequestDto = new ProfileUpdateControllerRequest();
        updateRequestDto.setUsername(signUpControllerRequestOne.getUsername());
        updateRequestDto.setEmail(signUpControllerRequestOne.getEmail());
        updateRequestDto.setPassword(signUpControllerRequestOne.getPassword());
        updateRequestDto.setCountry(signUpControllerRequestOne.getCountry());
        updateRequestDto.setBirthday(signUpControllerRequestOne.getBirthday());
        updateRequestDto.setDescription("");
    }

    @Test
    void T01_Sign_Up_Valid_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .signUp(signUpControllerRequestOne, HttpStatus.OK);

    }

    @Test
    void T02_Find_Signed_Up_User_Id_Fill_Update_Request(){

        UserControllerResponse responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(signUpControllerRequestOne.getUsername(),HttpStatus.OK);

        updateRequestDto.setId(responseDto.getId());


        Assertions.assertEquals(responseDto.getId(),updateRequestDto.getId());
    }

    @Test
    void T03_Update_User_Description_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setDescription("UPDATE TEST DESCRIPTION TEXT");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserControllerResponse responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assertions.assertEquals(updateRequestDto.getDescription(),responseDto.getDescription());

    }

    @Test
    void T04_Update_User_Email_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setEmail("UPDATEDEMAIL@gmail.com");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserControllerResponse responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assertions.assertEquals(updateRequestDto.getEmail(),responseDto.getEmail());

    }

    @Test
    void T05_Update_User_Password_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setPassword("UPDATED PASSWORD");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserControllerResponse responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

    }

    @Test
    void T06_Update_User_Country_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setCountry("ARMENIA");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserControllerResponse responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assertions.assertEquals(updateRequestDto.getCountry(),responseDto.getCountry());

    }

    @Test
    void T07_Update_User_Birthday_Should_Return_Status_OK_And_Update_Data() throws ParseException {


        Date birthday = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1995-06-23");

        updateRequestDto.setBirthday(birthday);

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserControllerResponse responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assertions.assertEquals(updateRequestDto.getBirthday(),responseDto.getBirthday());

    }

    @Test
    void T08_Update_User_With_Existing_Email_Should_Return_Status_CONFLICT() throws ParseException {


        updateRequestDto.setEmail("johndoe@gmail.com");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.CONFLICT);

    }

    @Test
    void T09_Delete_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .deleteUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

    }

}
