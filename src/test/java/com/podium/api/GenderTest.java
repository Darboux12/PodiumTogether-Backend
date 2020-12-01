package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.gender.GenderRequestDto;
import com.podium.model.dto.response.gender.GenderResponseDto;
import com.podium.validator.GenderValidator;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class GenderTest {

    private static GenderRequestDto requestDto;

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
        requestDto = new GenderRequestDto("TestGender");
    }

    @Test
    void T01_Add_Valid_Gender_ShouldReturnStatus_OK(){

        GenderValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

    }

    @Test
    void T02_Add_Same_Gender_Again_ShouldReturnStatus_CONFLICT(){

        GenderValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

    }

    @Test
    void T03_Add_Empty_Gender_ShouldReturnStatus_CONFLICT(){

        GenderValidator
                .getInstance()
                .add(new GenderRequestDto(""),HttpStatus.CONFLICT);

    }

    @Test
    void T04_Find_All_Gender_ShouldReturn_Iterable_Containing_Added_Gender(){

        boolean isPresent = GenderValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(GenderResponseDto::getGender)
                .anyMatch(requestDto.getGender()::equals);

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T05_Find_Created_Gender_ShouldReturnStatus_OK_Containing_Added_Gender(){

        GenderResponseDto responseDto =

                GenderValidator
                        .getInstance()
                        .findByName(requestDto.getGender(),HttpStatus.OK);

        Assertions.assertEquals(responseDto.getGender(),responseDto.getGender());

    }

    @Test
    void T06_Exist_Created_Gender_ShouldReturnStatus_OK(){

        GenderValidator
                .getInstance()
                .existGenderByName(requestDto.getGender(),HttpStatus.OK);

    }

    @Test
    void T07_Delete_Created_Gender_ShouldReturnStatus_OK(){

        GenderValidator
                .getInstance()
                .deleteGenderByName(requestDto.getGender(),HttpStatus.OK);

    }

    @Test
    void T08_Delete_Created_Gender_Again_ShouldReturnStatus_NOTFOUND(){

        GenderValidator
                .getInstance()
                .deleteGenderByName(requestDto.getGender(),HttpStatus.NOT_FOUND);

    }

}
