package com.podium.api;

import com.podium.constant.PodiumLimits;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.CityAddControllerRequest;
import com.podium.controller.dto.response.CityControllerResponse;
import com.podium.validator.CityValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class CitiesTest {

    private static Stream<String> provideCityNamesForTests(){

        return Stream.of(
                "TEST_CITY_NAME_ONE",
                "TEST_CITY_NAME_TWO",
                "TEST_CITY_NAME_THREE",
                "TEST_CITY_NAME_FOUR",
                "TEST_CITY_NAME_FIVE"
        );

    }

    private static Stream<String> provideEmptyCityNamesForTests(){

        return Stream.of("", " ", "  ", "       ");

    }

    private static Stream<String> provideTooLongAndTooShortCityNamesForTests(){

        return Stream.of(
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 1),
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 10),
                StringUtils.repeat("*", PodiumLimits.maxCityLength + 100),
                StringUtils.repeat("*", PodiumLimits.minCityLength - 1),
                StringUtils.repeat("*", PodiumLimits.minCityLength - 10),
                StringUtils.repeat("*", PodiumLimits.minCityLength - 100)
        );

    }



    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    @ParameterizedTest
    @MethodSource("provideCityNamesForTests")
    void T01_Add_Valid_City_Should_Return_Status_OK(String city){

        CityValidator
                .getInstance()
                .add(new CityAddControllerRequest(city),HttpStatus.OK);
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T02_Add_Same_City_Again_Should_Return_Status_CONFLICT(String city){
        CityValidator
                .getInstance()
                .add(new CityAddControllerRequest(city),HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyCityNamesForTests")
    void T03_Add_Empty_City_Should_Return_Status_CONFLICT(String city){

        CityValidator
                .getInstance()
                .add(new CityAddControllerRequest(city),HttpStatus.CONFLICT);
    }

    @ParameterizedTest
    @MethodSource("provideTooLongAndTooShortCityNamesForTests")
    void T04_Add_Too_Long_And_Too_Short_City_Should_Return_Status_CONFLICT(String city){

        CityValidator
                .getInstance()
                .add(new CityAddControllerRequest(city),HttpStatus.CONFLICT);
    }

    @Test
    void T06_Find_All_City_Should_Return_Status_OK_Containing_Added_Cities(){

        List<String> responseCities = CityValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CityControllerResponse::getCity)
                .collect(Collectors.toList());

        Assertions.assertTrue
                (responseCities.containsAll
                        (provideCityNamesForTests().collect(Collectors.toList())));

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T07_Find_City_By_Name_Should_Return_Status_OK_And_Added_City(String city){

        String cityName =

                CityValidator
                        .getInstance()
                        .findByName(city,HttpStatus.OK)
                        .getCity();

        Assertions.assertEquals(city,cityName);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_NOT_EXISTING_CITY"})
    void T10_Find_City_By_Name_Not_Exist_Should_Return_Status_NOT_FOUND(String city){

        CityValidator
                .getInstance()
                .findByName(city,HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T11_Exist_City_By_Name_Should_Return_Status_OK(String city){

        CityValidator
                .getInstance()
                .existCityByName(city,HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideCityNamesForTests")
    void T12_Delete_City_Should_Return_Status_OK_Deleting_Added_Cities(String city){

        CityValidator
                .getInstance()
                .deleteCityByName(city,HttpStatus.OK);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T13_Exist_Deleted_City_By_Name_Should_Return_FALSE(String city){

        boolean isPresent =

        CityValidator
                .getInstance()
                .existCityByName(city,HttpStatus.OK);

        Assertions.assertFalse(isPresent);

    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST_CITY_NAME_ONE","TEST_CITY_NAME_TWO"})
    void T14_Delete_Created_City_Again_Should_Return_Status_NOTFOUND(String city){

        CityValidator
                .getInstance()
                .deleteCityByName(city,HttpStatus.NOT_FOUND);
    }

}
