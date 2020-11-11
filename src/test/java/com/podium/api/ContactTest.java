package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.validator.ContactValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContactTest {

    private static ContactRequestDto contactRequestDTO;
    private static String valueHolder;
    private static int idHolder;

    @BeforeClass
    public static void beforeClass(){

        TestLogger.setUp();
        contactRequestDTO = new ContactRequestDto(
                "testemail@gmail.pl",
                "Technical",
                "Test contact message"
        );

    }

    @Test
    public void T01_Add_Valid_Contact_Should_Return_Status_OK(){

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.OK);
    }

    @Test
    public void T02_Add_Contact_Empty_Email_Should_Return_Status_CONFLICT(){

        valueHolder = contactRequestDTO.getUserEmail();
        contactRequestDTO.setUserEmail("");

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.CONFLICT);

        contactRequestDTO.setUserEmail(valueHolder);
    }

    @Test
    public void T03_Add_Contact_Empty_Subject_Should_Return_Status_CONFLICT(){

        valueHolder = contactRequestDTO.getSubject();
        contactRequestDTO.setSubject("");

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.CONFLICT);

        contactRequestDTO.setSubject(valueHolder);
    }

    @Test
    public void T04_Add_Contact_Empty_Message_Should_Return_Status_CONFLICT(){

        valueHolder = contactRequestDTO.getMessage();
        contactRequestDTO.setMessage("");

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.CONFLICT);

        contactRequestDTO.setMessage(valueHolder);
    }

    @Test
    public void T05_Add_Contact_To_Long_Message_Should_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxContactMessageLength + 1);

        valueHolder = contactRequestDTO.getMessage();
        contactRequestDTO.setMessage(toLong);

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.CONFLICT);

        contactRequestDTO.setMessage(valueHolder);
    }

    @Test
    public void T06_Add_Contact_To_Short_Message_Should_Return_Status_CONFLICT(){

        String toShort =
                StringUtils.repeat("*", PodiumLimits.minContactMessageLength - 1);

        valueHolder = contactRequestDTO.getMessage();
        contactRequestDTO.setMessage(toShort);

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.CONFLICT);

        contactRequestDTO.setMessage(valueHolder);
    }

    @Test
    public void T07_Add_Contact_To_Long_Email_Should_Return_Status_CONFLICT(){

        String toLong =
                StringUtils.repeat("*", PodiumLimits.maxEmailLength + 1);

        valueHolder = contactRequestDTO.getUserEmail();
        contactRequestDTO.setUserEmail(toLong);

        ContactValidator
                .getInstance()
                .add(contactRequestDTO,HttpStatus.CONFLICT);

        contactRequestDTO.setUserEmail(valueHolder);
    }

    @Test
    public void T08_Find_Contacts_By_Email_ShouldReturnStatus_OK_Iterable_Containing_Added_DTO(){

        boolean isPresent = ContactValidator
                .getInstance()
                .findByEmail(contactRequestDTO.getUserEmail(),HttpStatus.OK)
                .stream()
                .map(ContactResponseDto::getMessage)
                .anyMatch(contactRequestDTO.getMessage()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T09_Find_Contacts_By_Subject_ShouldReturnStatus_OK_Iterable_Containing_Added_DTO(){

        boolean isPresent = ContactValidator
                .getInstance()
                .findBySubject(contactRequestDTO.getSubject(),HttpStatus.OK)
                .stream()
                .map(ContactResponseDto::getMessage)
                .anyMatch(contactRequestDTO.getMessage()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T10_Find_All_Contact_Should_ShouldReturnStatus_OK_Iterable_Containing_Added_DTO(){

        boolean isPresent = ContactValidator
                .getInstance()
                .findAll()
                .stream()
                .map(ContactResponseDto::getMessage)
                .anyMatch(contactRequestDTO.getMessage()::equals);

        Assert.assertTrue(isPresent);
    }

    @Test
    public void T11_Delete_Contact_Should_Return_Status_OK(){

        ContactResponseDto contact = ContactValidator.getInstance()
                .findByEmail
                        (contactRequestDTO.getUserEmail(),HttpStatus.OK)
                .stream()
                        .filter(x -> x.getMessage()
                                .equals(contactRequestDTO.getMessage()))
                .findFirst().orElse(null);

        if (contact != null) {
            idHolder = contact.getId();

            ContactValidator
                    .getInstance()
                    .deleteContactById(contact.getId(),HttpStatus.OK);
        }

    }

    @Test
    public void T12_Delete_Contact_Again_Should_Return_Status_NOT_FOUND(){

        ContactValidator
                .getInstance()
                .deleteContactById(idHolder,HttpStatus.NOT_FOUND);

    }

}
