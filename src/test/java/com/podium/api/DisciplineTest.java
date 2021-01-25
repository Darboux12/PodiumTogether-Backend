package com.podium.api;

import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.DisciplineAddControllerRequest;
import com.podium.controller.dto.response.DisciplineControllerResponse;
import com.podium.constant.PodiumLimits;
import com.podium.validator.DisciplineValidator;
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
class DisciplineTest {

    private static String token = "";

    private static Stream<String> provideDisciplinesForTests(){

        return Stream.of(
                "TEST DISCIPLINE ONE",
                "TEST DISCIPLINE TWO",
                "TEST DISCIPLINE THREE"
        );

    }

    private static Stream<String> provideToShortDisciplinesForTests(){

        return Stream.of(
                StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1),
                StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 10),
                StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 100)
        );

    }

    private static Stream<String> provideToLongDisciplinesForTests(){

        return Stream.of(
                StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1),
                StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 10),
                StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 100)
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
    @MethodSource("provideDisciplinesForTests")
    void T02_Add_Valid_Discipline_Should_Return_Status_OK(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineAddControllerRequest(discipline),token,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T03_Add_Discipline_Empty_Name_Should_Return_Status_CONFLICT(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineAddControllerRequest(discipline),token,HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideToShortDisciplinesForTests")
    void T04_Add_Discipline_Too_Short_Name_Should_Return_Status_CONFLICT(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineAddControllerRequest(discipline),token,HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideToLongDisciplinesForTests")
    void T05_Add_Discipline_Too_Long_Name_Should_Return_Status_CONFLICT(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineAddControllerRequest(discipline),token,HttpStatus.CONFLICT);

    }

    @Test
    void T06_Find_All_Discipline_Should_Return_Status_OK_Containing_Added_Disciplines(){

        List<String> responseDisciplines = DisciplineValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(DisciplineControllerResponse::getDiscipline)
                .collect(Collectors.toList());

        Assertions.assertTrue
                ( responseDisciplines .containsAll
                        (provideDisciplinesForTests().collect(Collectors.toList())));
    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T07_Find_Discipline_By_Name_ShouldReturn_Status_OK_Containing_Added_Discipline(String discipline){

        DisciplineControllerResponse responseDto =

                DisciplineValidator
                        .getInstance()
                        .findByName(discipline,HttpStatus.OK);

        Assertions.assertEquals(discipline,responseDto.getDiscipline());

    }

    @Test
    void T08_Find_Discipline_By_Name_Not_Exist_ShouldReturn_Status_NOT_FOUND(){

        DisciplineValidator
                .getInstance()
                .findByName("NOT EXISTING DISCIPLINE",HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T09_Exist_Discipline_By_Name_ShouldReturn_TRUE(String discipline){

        boolean isPresent =

        DisciplineValidator
                .getInstance()
                .existDisciplineByName(discipline,HttpStatus.OK);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T10_Exist_Discipline_By_Name_Not_Exist_ShouldReturn_FALSE(){

        boolean isPresent =

        DisciplineValidator
                .getInstance()
                .existDisciplineByName("NOT EXISTING DISCIPLINE",HttpStatus.OK);

        Assertions.assertFalse(isPresent);

    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T11_deleteCreatedDiscipline_ShouldReturnStatus_OK(String discipline){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(discipline,token,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T12_deleteCreatedDisciplineAgain_ShouldReturnStatus_NOTFOUND(String discipline){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(discipline,token,HttpStatus.NOT_FOUND);

    }

}
