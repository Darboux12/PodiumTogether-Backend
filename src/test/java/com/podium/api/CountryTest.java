package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.localization.CountryRequestDto;
import com.podium.model.dto.response.localization.CountryResponseDto;
import com.podium.validator.CountryValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class CountryTest {

    @BeforeAll
    static void beforeClass(){

        TestLogger.setUp();

    }

    private static Stream<CountryRequestDto> provideCountriesForTests(){

        return Stream.of(

               new CountryRequestDto("QQ",
                       "COUNTRY_ONE",
                       "COUNTRY_ONE_PRINTABLE",
                       "QQQ",
                       0
               ),

                new CountryRequestDto("YY",
                        "COUNTRY_TWO",
                        "COUNTRY_TWO_PRINTABLE",
                        "YYY",
                        1
                ),

                new CountryRequestDto("XX",
                        "COUNTRY_THREE",
                        "COUNTRY_THREE_PRINTABLE",
                        "XXX",
                        2
                )

        );

    }

    private static Stream<CountryRequestDto> provideCountriesEmptyValuesForTests(){

        return Stream.of(

                new CountryRequestDto("",
                        "COUNTRY_ONE",
                        "COUNTRY_ONE_PRINTABLE",
                        "QQQ",
                        0
                ),

                new CountryRequestDto("YY",
                        "",
                        "COUNTRY_TWO_PRINTABLE",
                        "YYY",
                        1
                ),

                new CountryRequestDto("XX",
                        "COUNTRY_THREE",
                        "",
                        "XXX",
                        2
                ),

                new CountryRequestDto("XX",
                        "COUNTRY_THREE",
                        "COUNTRY_TWO_PRINTABLE",
                        "",
                        2
                ),

                new CountryRequestDto("",
                        "",
                        "",
                        "",
                        0
                )

        );

    }

    @ParameterizedTest
    @MethodSource("provideCountriesForTests")
    void T01_Add_Valid_Countries_Should_Return_Status_OK(CountryRequestDto requestDto){

        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

    }

    @Test
    void T02_Find_All_Country_Should_Return_Status_OK_Iterable_Containing_Added_Countries(){

        List<String> actualCountries = CountryValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CountryResponseDto::getName)
                .collect(Collectors.toList());

        List<String> expectedCountries = provideCountriesForTests()
                .map(CountryRequestDto::getName).collect(Collectors.toList());

        Assertions.assertTrue(actualCountries.containsAll(expectedCountries));

    }

    @ParameterizedTest
    @MethodSource("provideCountriesForTests")
    void T03_Delete_Created_Countries_ShouldReturnStatus_OK(CountryRequestDto requestDto){

        CountryValidator
                .getInstance()
                .delete(requestDto.getName(),HttpStatus.OK);

    }

    @ParameterizedTest
    @MethodSource("provideCountriesForTests")
    void T04_Delete_Created_Countries_Again_ShouldReturnStatus_NOTFOUND(CountryRequestDto requestDto){

        CountryValidator
                .getInstance()
                .delete(requestDto.getName(),HttpStatus.NOT_FOUND);

    }

    @ParameterizedTest
    @MethodSource("provideCountriesEmptyValuesForTests")
    void T05_Add_Country_Empty_Name_ShouldReturnStatus_CONFLICT(CountryRequestDto requestDto){

        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

    }

}
