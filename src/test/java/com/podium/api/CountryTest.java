package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.validator.CountryValidator;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class CountryTest {

    private static CountryRequestDto requestDto;
    private static String valueHolder;

    @BeforeAll
    public static void beforeClass(){

        TestLogger.setUp();

        requestDto = new CountryRequestDto(
                "QQ",
                "TestCountry",
                "PrintableTestName",
                "QQQ",
                0
        );

    }

    @Test
    public void T01_Add_Valid_Country_Should_Return_Status_OK(){

        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

    }

    @Test
    public void T02_Find_All_Country_Should_Return_Status_OK_Iterable_Country(){

        boolean isPresent = CountryValidator
                .getInstance()
                .findAll()
                .stream()
                .map(CountryResponseDto::getName)
                .anyMatch(requestDto.getName()::equals);

        Assertions.assertTrue(isPresent);

    }

    @Test
    public void T03_Delete_Created_Country_ShouldReturnStatus_OK(){

        CountryValidator
                .getInstance()
                .delete(requestDto.getName(),HttpStatus.OK);

    }

    @Test
    public void T04_Delete_Created_Country_Again_ShouldReturnStatus_NOTFOUND(){

        CountryValidator
                .getInstance()
                .delete(requestDto.getName(),HttpStatus.NOT_FOUND);

    }

    @Test
    public void T05_Add_Country_Empty_Name_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getName();
        requestDto.setName("");

        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setName(valueHolder);

    }

    @Test
    public void T06_Add_Country_Empty_Id_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getCountryId();
        requestDto.setCountryId("");


        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setCountryId(valueHolder);

    }

    @Test
    public void T07_Add_Country_Empty_Printable_Name_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getPrintableName();
        requestDto.setPrintableName("");


        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setPrintableName(valueHolder);

    }

    @Test
    public void T08_Add_Country_Empty_Iso3_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getIso3();
        requestDto.setIso3("");

        CountryValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setIso3(valueHolder);

    }

}
