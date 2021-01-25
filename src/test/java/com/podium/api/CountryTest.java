package com.podium.api;

import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.CountryAddControllerRequest;
import com.podium.controller.dto.response.CountryControllerResponse;
import com.podium.validator.CountryValidator;
import com.podium.validator.UserValidator;
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

    @Test
    void T01_Find_All_Country_Containing_Countries(){

        int countriesNumber =

        CountryValidator
                .getInstance()
                .findAll(HttpStatus.OK).size();

        Assertions.assertEquals(239,countriesNumber);

    }

    @Test
    void T02_Exist_Country_By_Name_GERMANY(){

        boolean isPresent =
        CountryValidator.getInstance().existCountryByName("GERMANY",HttpStatus.OK);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T03_Find_Country_By_Name_GERMANY(){

        String name =
                CountryValidator
                        .getInstance()
                        .findCountryByName("GERMANY",HttpStatus.OK)
                .getName();

        Assertions.assertEquals("GERMANY",name);

    }

}
