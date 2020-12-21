package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.ContactAddControllerRequest;
import com.podium.controller.dto.response.ContactControllerResponse;
import com.podium.validator.ContactValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class ContactTest {

    private static int idHolder;

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    private static Stream<ContactAddControllerRequest> provideContactsForTests(){

        return Stream.of(

                new ContactAddControllerRequest(
                        "TEST_ONE@gmail.pl",
                        "Technical",
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_TWO@gmail.pl",
                        "Feedback",
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_THREE@gmail.pl",
                        "Review",
                        "Test contact message"
                )

        );

    }

    private static Stream<ContactAddControllerRequest> provideEmptyContactsNamesForTests(){

        return Stream.of(

                new ContactAddControllerRequest(
                        "     ",
                        "Technical",
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_TWO@gmail.pl",
                        "   ",
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_THREE@gmail.pl",
                        "Technical",
                        ""
                ),

                new ContactAddControllerRequest(
                        "   ",
                        "",
                        "   "
                ),

                new ContactAddControllerRequest(
                        "       ",
                        "           ",
                        "             "
                )

        );

    }

    private static Stream<ContactAddControllerRequest> provideTooLongAndTooShortContactsValuesForTests(){

        return Stream.of(

                new ContactAddControllerRequest(
                        StringUtils.repeat("*", PodiumLimits.maxEmailLength + 1),
                        "Technical",
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_ONE@gmail.pl",
                        StringUtils.repeat("*", PodiumLimits.maxSubjectLength + 1),
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_ONE@gmail.pl",
                        StringUtils.repeat("*", PodiumLimits.minSubjectLength - 1),
                        "Test contact message"
                ),

                new ContactAddControllerRequest(
                        "TEST_ONE@gmail.pl",
                        "Technical",
                        StringUtils.repeat("*", PodiumLimits.maxContactMessageLength + 1)
                ),

                new ContactAddControllerRequest(
                        "TEST_ONE@gmail.pl",
                        "Technical",
                        StringUtils.repeat("*", PodiumLimits.minContactMessageLength - 1)
                )

        );

    }

    @ParameterizedTest
    @MethodSource("provideContactsForTests")
    void T01_Add_Valid_Contacts_Return_Status_OK(ContactAddControllerRequest requestDto){

        ContactValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);
    }

    @ParameterizedTest
    @MethodSource("provideContactsForTests")
    void T02_Find_Contacts_By_Email_Return_Status_OK_Iterable_Containing_Added_DTO(ContactAddControllerRequest requestDto){

        boolean isPresent = ContactValidator
                .getInstance()
                .findByEmail(requestDto.getUserEmail(),HttpStatus.OK)
                .stream()
                .map(ContactControllerResponse::getEmail)
                .anyMatch(requestDto.getUserEmail()::equals);

        Assertions.assertTrue(isPresent);

    }

    @ParameterizedTest
    @MethodSource("provideContactsForTests")
    void T03_Find_Contacts_By_Subject_Return_Status_OK_Iterable_Containing_Added_DTO(ContactAddControllerRequest requestDto){

        boolean isPresent =

                ContactValidator
                .getInstance()
                .findBySubject(requestDto.getSubject(),HttpStatus.OK)
                .stream()
                .map(ContactControllerResponse::getEmail)
                .anyMatch(requestDto.getUserEmail()::equals);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T04_Find_All_Contact_Return_Status_OK_Iterable_Containing_Added_DTO(){

        List<String> expectedEmails = provideContactsForTests()
                .map(ContactAddControllerRequest::getUserEmail).collect(Collectors.toList());

        List<String> actualEmails = ContactValidator
                .getInstance()
                .findAll()
                .stream()
                .map(ContactControllerResponse::getEmail)
                .collect(Collectors.toList());

        Assertions.assertTrue(actualEmails.containsAll(expectedEmails));
    }

    @ParameterizedTest
    @MethodSource("provideContactsForTests")
    void T05_Delete_Contacts_Should_Return_Status_OK(ContactAddControllerRequest requestDto){

        ContactControllerResponse contact = ContactValidator.getInstance()
                .findByEmail
                        (requestDto.getUserEmail(),HttpStatus.OK)
                .stream()
                .filter(x -> x.getEmail()
                        .equals(requestDto.getUserEmail()))
                .findFirst().orElse(null);

        if (contact != null) {
            idHolder = contact.getId();

            ContactValidator
                    .getInstance()
                    .deleteContactById(contact.getId(),HttpStatus.OK);
        }

    }

    @Test
    void T06_Delete_Contact_Again_Should_Return_Status_NOT_FOUND(){

        ContactValidator
                .getInstance()
                .deleteContactById(idHolder,HttpStatus.NOT_FOUND);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyContactsNamesForTests")
    void T07_Add_Contact_Empty_Field_Should_Return_Status_CONFLICT(ContactAddControllerRequest requestDto){

        ContactValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortContactsValuesForTests")
    void T08_Add_Contact_Too_Long_Too_Short_Values_Should_Return_Status_CONFLICT(ContactAddControllerRequest requestDto){

        ContactValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

    }

}
