package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.constant.PodiumLimits;
import com.podium.validator.CityValidator;
import com.podium.validator.DisciplineValidator;
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

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T01_Add_Valid_Discipline_Should_Return_Status_OK(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineRequestDto(discipline),HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T02_Add_Discipline_Empty_Name_Should_Return_Status_CONFLICT(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineRequestDto(discipline),HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideToShortDisciplinesForTests")
    void T03_Add_Discipline_Too_Short_Name_Should_Return_Status_CONFLICT(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineRequestDto(discipline),HttpStatus.CONFLICT);

    }

    @ParameterizedTest
    @MethodSource("provideToLongDisciplinesForTests")
    void T04_Add_Discipline_Too_Long_Name_Should_Return_Status_CONFLICT(String discipline){

        DisciplineValidator
                .getInstance()
                .add(new DisciplineRequestDto(discipline),HttpStatus.CONFLICT);

    }

    @Test
    void T05_Find_All_Discipline_Should_Return_Status_OK_Containing_Added_Disciplines(){

        List<String> responseDisciplines = DisciplineValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(DisciplineResponseDto::getDiscipline)
                .collect(Collectors.toList());

        Assertions.assertTrue
                ( responseDisciplines .containsAll
                        (provideDisciplinesForTests().collect(Collectors.toList())));
    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T06_Find_Discipline_By_Name_ShouldReturn_Status_OK_Containing_Added_Discipline(String discipline){

        DisciplineResponseDto responseDto =

                DisciplineValidator
                        .getInstance()
                        .findByName(discipline,HttpStatus.OK);

        Assertions.assertEquals(discipline,responseDto.getDiscipline());

    }

    @Test
    void T07_Find_Discipline_By_Name_Not_Exist_ShouldReturn_Status_NOT_FOUND(){

        DisciplineValidator
                .getInstance()
                .findByName("NOT EXISTING DISCIPLINE",HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T08_Exist_Discipline_By_Name_ShouldReturn_TRUE(String discipline){

        boolean isPresent =

        DisciplineValidator
                .getInstance()
                .existDisciplineByName(discipline,HttpStatus.OK);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T09_Exist_Discipline_By_Name_Not_Exist_ShouldReturn_FALSE(){

        boolean isPresent =

        DisciplineValidator
                .getInstance()
                .existDisciplineByName("NOT EXISTING DISCIPLINE",HttpStatus.OK);

        Assertions.assertFalse(isPresent);

    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T10_deleteCreatedDiscipline_ShouldReturnStatus_OK(String discipline){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(discipline,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideDisciplinesForTests")
    void T11_deleteCreatedDisciplineAgain_ShouldReturnStatus_NOTFOUND(String discipline){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(discipline,HttpStatus.NOT_FOUND);

    }

}
