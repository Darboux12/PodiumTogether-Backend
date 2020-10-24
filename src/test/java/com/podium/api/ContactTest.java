package com.podium.api;

import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.validator.ContactValidator;
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
public class ContactTest {

    private static ContactRequestDto contactRequestDTO;

    @BeforeClass
    public static void beforeClass(){

        TestLogger.setUp();
        contactRequestDTO = Constant.getValidContactRequestDto();

    }

    @Test
    public void T01_Add_Valid_Contact_Should_Return_Status_OK() throws ParseException {

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.OK);
    }

    @Test
    public void T02_Find_Contact_By_Email_ShouldReturnStatus_OK_Iterable_DTO() throws ParseException {

        boolean isPresent = ContactValidator
                .getInstance()
                .findByEmail(contactRequestDTO.getUserEmail(),HttpStatus.OK)
                .stream()
                .map(ContactResponseDto::getMessage)
                .anyMatch(contactRequestDTO.getMessage()::equals);

        Assert.assertTrue(isPresent);


    }

    @Test
    public void T03_Find_Contact_By_Subject_ShouldReturnStatus_OK_Iterable_DTO() throws ParseException {

        boolean isPresent = ContactValidator
                .getInstance()
                .findBySubject(contactRequestDTO.getSubject(),HttpStatus.OK)
                .stream()
                .map(ContactResponseDto::getMessage)
                .anyMatch(contactRequestDTO.getMessage()::equals);

        Assert.assertTrue(isPresent);


    }

    @Test
    public void T07_Find_All_Contact_Should_ShouldReturnStatus_OK_Iterable_DTO() throws ParseException {

        boolean isPresent = ContactValidator
                .getInstance()
                .findAll()
                .stream()
                .map(ContactResponseDto::getMessage)
                .anyMatch(contactRequestDTO.getMessage()::equals);

        Assert.assertTrue(isPresent);
    }

    @Test
    public void T08_Delete_Contact_Should_Return_Status_200() throws ParseException {

        ContactResponseDto contact =
                ContactValidator
                .getInstance()
                .findByEmail(contactRequestDTO.getUserEmail(),HttpStatus.OK)
                .stream()
                        .filter(x -> x.getMessage()
                                .equals(contactRequestDTO.getMessage()))
                .findFirst().orElse(null);

        assert contact != null;
        ContactValidator
                .getInstance()
                .deleteContactById(contact.getId(),HttpStatus.OK);

    }

}
