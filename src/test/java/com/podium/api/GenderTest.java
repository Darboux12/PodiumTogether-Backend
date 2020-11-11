package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.validator.GenderValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GenderTest {

    private static GenderRequestDto requestDto;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = new GenderRequestDto("TestGender");
    }

    @Test
    public void T01_Add_Valid_Gender_ShouldReturnStatus_OK(){

        GenderValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

    }

    @Test
    public void T02_Add_Same_Gender_Again_ShouldReturnStatus_CONFLICT(){

        GenderValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

    }

    @Test
    public void T03_Add_Empty_Gender_ShouldReturnStatus_CONFLICT(){

        GenderValidator
                .getInstance()
                .add(new GenderRequestDto(""),HttpStatus.CONFLICT);

    }

    @Test
    public void T04_Find_All_Gender_ShouldReturn_Iterable_Containing_Added_Gender(){

        boolean isPresent = GenderValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(GenderResponseDto::getGender)
                .anyMatch(requestDto.getGender()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T05_Find_Created_Gender_ShouldReturnStatus_OK_Containing_Added_Gender(){

        GenderResponseDto responseDto =

                GenderValidator
                        .getInstance()
                        .findByName(requestDto.getGender(),HttpStatus.OK);

        Assert.assertEquals(responseDto.getGender(),responseDto.getGender());

    }

    @Test
    public void T06_Exist_Created_Gender_ShouldReturnStatus_OK(){

        GenderValidator
                .getInstance()
                .existGenderByName(requestDto.getGender(),HttpStatus.OK);

    }

    @Test
    public void T07_Delete_Created_Gender_ShouldReturnStatus_OK(){

        GenderValidator
                .getInstance()
                .deleteGenderByName(requestDto.getGender(),HttpStatus.OK);

    }

    @Test
    public void T08_Delete_Created_Gender_Again_ShouldReturnStatus_NOTFOUND(){

        GenderValidator
                .getInstance()
                .deleteGenderByName(requestDto.getGender(),HttpStatus.NOT_FOUND);

    }

}
