package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.GenderAddControllerRequest;
import com.podium.controller.dto.response.GenderControllerResponse;
import com.podium.validator.GenderValidator;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class GenderTest {

    private static String token = "";

    private static Stream<String> provideGendersForTests(){

        return Stream.of(
                "TEST GENDER ONE",
                "TEST GENDER TWO",
                "TEST GENDER THREE"
        );

    }

    private static Stream<String> provideToShortGendersForTests(){

        return Stream.of(
                StringUtils.repeat("*", PodiumLimits.minGenderLength - 1),
                StringUtils.repeat("*", PodiumLimits.minGenderLength - 10),
                StringUtils.repeat("*", PodiumLimits.minGenderLength - 100)
        );

    }

    private static Stream<String> provideToLongGendersForTests(){

        return Stream.of(
                StringUtils.repeat("*", PodiumLimits.maxGenderLength + 1),
                StringUtils.repeat("*", PodiumLimits.maxGenderLength + 10),
                StringUtils.repeat("*", PodiumLimits.maxGenderLength + 100)
        );

    }

    private static Stream<String> provideEmptyValuesForTests(){
        return Stream.of(""," ", "  ","  ","         ","             ");
    }

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    @Test
    void T01_Sign_In_As_Admin_User_Get_Token(){

        token =

                UserValidator
                        .getInstance()
                        .signIn(new JwtControllerRequest("admin","adminadmin"),HttpStatus.OK)
                        .getToken();

    }

    @ParameterizedTest
    @MethodSource("provideGendersForTests")
    void T02_Add_Valid_Gender_ShouldReturnStatus_OK(String gender){

        GenderValidator
                .getInstance()
                .add(new GenderAddControllerRequest(gender),token,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideGendersForTests")
    void T03_Add_Same_Gender_Again_ShouldReturnStatus_CONFLICT(String gender){

        GenderValidator
                .getInstance()
                .add(new GenderAddControllerRequest(gender),token,HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T04_Add_Empty_Gender_ShouldReturnStatus_CONFLICT(String gender){

        GenderValidator
                .getInstance()
                .add(new GenderAddControllerRequest(gender),token,HttpStatus.CONFLICT);

    }

    @Test
    void T05_Find_All_Gender_ShouldReturn_Iterable_Containing_Added_Genders(){

        List<String> responseGenders = GenderValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(GenderControllerResponse::getGender)
                .collect(Collectors.toList());

        Assertions.assertTrue
                (responseGenders.containsAll
                        (provideGendersForTests().collect(Collectors.toList())));

    }

    @ParameterizedTest
    @MethodSource("provideGendersForTests")
    void T06_Find_Created_Gender_ShouldReturnStatus_OK_Containing_Added_Gender(String gender){

        GenderControllerResponse responseDto =

                GenderValidator
                        .getInstance()
                        .findByName(gender,HttpStatus.OK);

        Assertions.assertEquals(gender,responseDto.getGender());

    }

    @ParameterizedTest
    @MethodSource("provideGendersForTests")
    void T07_Exist_Created_Gender_ShouldReturnStatus_TRUE(String gender){

        boolean isPresent =

                GenderValidator
                        .getInstance()
                        .existGenderByName(gender,HttpStatus.OK);

        Assertions.assertTrue(isPresent);

    }

    @ParameterizedTest
    @MethodSource("provideGendersForTests")
    void T08_Delete_Created_Gender_ShouldReturnStatus_OK(String gender){

        GenderValidator
                .getInstance()
                .deleteGenderByName(gender,token,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideGendersForTests")
    void T09_Delete_Created_Gender_Again_ShouldReturnStatus_NOTFOUND(String gender){

        GenderValidator
                .getInstance()
                .deleteGenderByName(gender,token,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideToShortGendersForTests")
    void T10_Add_Discipline_Too_Short_Name_Should_Return_Status_CONFLICT(String gender){

        GenderValidator
                .getInstance()
                .add(new GenderAddControllerRequest(gender),token,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideToLongGendersForTests")
    void T11_Add_Discipline_Too_Long_Name_Should_Return_Status_CONFLICT(String gender){

        GenderValidator
                .getInstance()
                .add(new GenderAddControllerRequest(gender),token,HttpStatus.CONFLICT);

    }

}
