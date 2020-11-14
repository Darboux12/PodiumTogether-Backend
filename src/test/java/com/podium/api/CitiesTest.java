package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.entity.localization.City;
import com.podium.validator.CityValidator;
import org.apache.commons.lang3.StringUtils;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class CitiesTest {

    private static CityRequestDto requestDto;

    private static Stream<Arguments> provideArgumentsForAddTest(){

        return Stream.of(
                Arguments.of("",HttpStatus.CONFLICT),
                Arguments.of(" ",HttpStatus.CONFLICT),
                Arguments.of("  ",HttpStatus.CONFLICT)

        );

    }

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
        requestDto = new CityRequestDto("TestCityName");
    }

    @Test
    void T01_Add_Valid_City_Should_Return_Status_OK(){

        CityValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);
    }

    @Test
    void T02_Add_Same_City_Again_Should_Return_Status_CONFLICT(){
        CityValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForAddTest")
    void T03_Add_Empty_City_Should_Return_Status_CONFLICT(String city,HttpStatus status){

        CityValidator
                .getInstance()
                .add(new CityRequestDto(city),status);
    }

    @Test
    void T04_Add_To_Long_City_Should_Return_Status_CONFLICT(){

        String toLongCityName =
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 1);

        CityValidator
                .getInstance()
                .add(new CityRequestDto(toLongCityName),HttpStatus.CONFLICT);
    }

    @Test
    void T05_Add_To_Short_City_Should_Return_Status_CONFLICT(){

        String toShortCityName =
                StringUtils.repeat("*", PodiumLimits.minCityLength - 1);

        CityValidator
                .getInstance()
                .add(new CityRequestDto(toShortCityName),HttpStatus.CONFLICT);
    }

    @Test
    void T06_Find_All_City_Should_Return_Status_OK_Containing_Added_City_Name(){

        boolean isPresent = CityValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CityResponseDto::getCity)
                .anyMatch(requestDto.getCity()::equals);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T07_Find_All_City_Should_Return_Status_OK_Containing_Katowice_Cairo(){

        List<String> responseCities = CityValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CityResponseDto::getCity)
                .collect(Collectors.toList());

        Assertions.assertTrue
                (responseCities.containsAll(List.of("Cairo","Katowice")));

    }

    @Test
    void T09_Find_City_By_Name_Should_Return_Status_OK_And_Added_City(){

        String cityName =

                CityValidator
                        .getInstance()
                        .findByName(requestDto.getCity(),HttpStatus.OK)
                        .getCity();

        Assertions.assertEquals(requestDto.getCity(),cityName);

    }

    @Test
    void T10_Find_City_By_Name_Not_Exist_Should_Return_Status_NOT_FOUND(){

        CityValidator
                .getInstance()
                .findByName("NOT EXISTING CITY NAME",HttpStatus.NOT_FOUND);

    }

    @Test
    void T11_Exist_City_By_Name_Should_Return_Status_OK(){

        CityValidator
                .getInstance()
                .existCityByName(requestDto.getCity(),HttpStatus.OK);

    }

    @Test
    void T12_Delete_City_Should_Return_Status_OK_Deleting_Created_City(){

        CityValidator
                .getInstance()
                .deleteCityByName(requestDto.getCity(),HttpStatus.OK);

    }

    @Test
    void T13_Exist_Deleted_City_By_Name_Should_Return_Status_BAD_REQUEST(){

        CityValidator
                .getInstance()
                .existCityByName(requestDto.getCity(),HttpStatus.BAD_REQUEST);

    }

    @Test
    void T14_Delete_Created_City_Again_Should_Return_Status_NOTFOUND(){

        CityValidator
                .getInstance()
                .deleteCityByName(requestDto.getCity(),HttpStatus.NOT_FOUND);
    }

}
