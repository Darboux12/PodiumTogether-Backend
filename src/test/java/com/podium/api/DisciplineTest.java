package com.podium.api;

import com.podium.constant.PodiumEndpoint;
import com.podium.helper.*;
import com.podium.logger.TestLogger;
import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.specification.TestSpecification;
import com.podium.constant.PodiumLimits;
import com.podium.validator.DisciplineValidator;
import com.podium.validator.UserValidator;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DisciplineTest {

   private static DisciplineRequestDto requestDto;
   private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){
        TestLogger.setUp();
        requestDto = Constant.getValidDisciplineRequestDto();
    }

    @Test
    public void T01_addValidDiscipline_ShouldReturnStatus_OK(){

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);



    }

    @Test
    public void T02_getAllDiscipline_ShouldReturnStatus_OK(){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .when().get(Path.server + PodiumEndpoint.findAllNews)
                .then().assertThat()
                .statusCode(HttpStatus.OK.value())
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T03_find_All_Discipline_ShouldReturnIterable_DisciplineResponse(){

        boolean isPresent = DisciplineValidator
                .getInstance()
                .findAll()
                .stream()
                .map(DisciplineResponseDto::getDiscipline)
                .anyMatch(requestDto.getDiscipline()::equals);

        Assert.assertTrue(isPresent);

    }

    @Test
    public void T04_find_Discipline_By_Name_ShouldReturn_DisciplineResponse(){

        DisciplineResponseDto responseDto =

                DisciplineValidator
                        .getInstance()
                        .findByName(requestDto.getDiscipline(),HttpStatus.OK);

        Assert.assertEquals(responseDto.getDiscipline(),responseDto.getDiscipline());

    }

    @Test
    public void T05_exist_Discipline_By_Name_ShouldReturn_Status_OK(){

        DisciplineValidator
                .getInstance()
                .existDisciplineByName(requestDto.getDiscipline(),HttpStatus.OK);

    }

    @Test
    public void T06_deleteCreatedDiscipline_ShouldReturnStatus_OK(){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(requestDto.getDiscipline(),HttpStatus.OK);


    }

    @Test
    public void T07_deleteCreatedDisciplineAgain_ShouldReturnStatus_NOTFOUND(){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(requestDto.getDiscipline(),HttpStatus.NOT_FOUND);

    }

    @Test
    public void T08_addDisciplineEmptyName_ShouldReturnStatus_CONFLICT(){

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline("");

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T09_addDisciplineToShortName_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toShort);

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

       requestDto.setDiscipline(valueHolder);

    }

    @Test
    public void T10_addDisciplineToLongName_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toLong);

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

       requestDto.setDiscipline(valueHolder);

    }

}
