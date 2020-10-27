package com.podium.api;

import com.podium.helper.Constant;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.ProfileUpdateRequestDto;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.validator.UserValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProfileUpdateTest {

    private static SignUpRequestDto signUpRequestDtoOne;
    private static ProfileUpdateRequestDto updateRequestDto;

    @BeforeClass
    public static void beforeClass() throws ParseException {
        TestLogger.setUp();
        signUpRequestDtoOne = Constant.getValidSignUpRequestDtoOne();
        updateRequestDto = new ProfileUpdateRequestDto();
        updateRequestDto.setUsername(signUpRequestDtoOne.getUsername());
        updateRequestDto.setEmail(signUpRequestDtoOne.getEmail());
        updateRequestDto.setPassword(signUpRequestDtoOne.getPassword());
        updateRequestDto.setCountry(signUpRequestDtoOne.getCountry());
        updateRequestDto.setBirthday(signUpRequestDtoOne.getBirthday());
        updateRequestDto.setDescription("");
    }

    @Test
    public void T01_Sign_Up_Valid_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .signUp(signUpRequestDtoOne, HttpStatus.OK);

    }

    @Test
    public void T02_Find_Signed_Up_User_Id_Fill_Update_Request(){

        UserResponseDto responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(signUpRequestDtoOne.getUsername(),HttpStatus.OK);

        updateRequestDto.setId(responseDto.getId());


        Assert.assertEquals(responseDto.getId(),updateRequestDto.getId());
    }

    @Test
    public void T03_Update_User_Description_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setDescription("UPDATE TEST DESCRIPTION TEXT");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserResponseDto responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assert.assertEquals(updateRequestDto.getDescription(),responseDto.getDescription());

    }

    @Test
    public void T04_Update_User_Email_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setEmail("UPDATEDEMAIL@gmail.com");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserResponseDto responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assert.assertEquals(updateRequestDto.getEmail(),responseDto.getEmail());

    }

    @Test
    public void T05_Update_User_Password_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setPassword("UPDATED PASSWORD");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserResponseDto responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

    }

    @Test
    public void T06_Update_User_Country_Should_Return_Status_OK_And_Update_Data(){

        updateRequestDto.setCountry("Armenia");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserResponseDto responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assert.assertEquals(updateRequestDto.getCountry(),responseDto.getCountry());

    }

    @Test
    public void T07_Update_User_Birthday_Should_Return_Status_OK_And_Update_Data() throws ParseException {


        Date birthday = new SimpleDateFormat(
                "yyyy-MM-dd").parse("1995-06-23");

        updateRequestDto.setBirthday(birthday);

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.OK);

        UserResponseDto responseDto =

                UserValidator
                        .getInstance()
                        .findUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

        Assert.assertEquals(updateRequestDto.getBirthday(),responseDto.getBirthday());

    }

    @Test
    public void T08_Update_User_With_Existing_Email_Should_Return_Status_CONFLICT() throws ParseException {


        updateRequestDto.setEmail("johndoe@gmail.com");

        UserValidator
                .getInstance()
                .updateUser(updateRequestDto,HttpStatus.CONFLICT);

    }

    @Test
    public void T09_Delete_User_Should_Return_Status_OK() throws ParseException {

        UserValidator
                .getInstance()
                .deleteUserByUsername(updateRequestDto.getUsername(),HttpStatus.OK);

    }











}
