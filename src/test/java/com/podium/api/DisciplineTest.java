package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.discipline.DisciplineRequestDto;
import com.podium.model.dto.response.discipline.DisciplineResponseDto;
import com.podium.constant.PodiumLimits;
import com.podium.validator.DisciplineValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class DisciplineTest {

   private static DisciplineRequestDto requestDto;
   private static String valueHolder;

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
        requestDto = new DisciplineRequestDto("TestDiscipline");
    }

    @Test
    void T01_Add_Valid_Discipline_Should_Return_Status_OK(){

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.OK);

    }

    @Test
    void T02_Add_Discipline_Empty_Name_Should_Return_Status_CONFLICT(){

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline("");

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    void T03_Add_Discipline_Too_Short_Name_Should_Return_Status_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minDisciplineLength - 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toShort);

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    void T04_Add_Discipline_Too_Long_Name_Should_Return_Status_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxDisciplineLength + 1);

        valueHolder = requestDto.getDiscipline();
        requestDto.setDiscipline(toLong);

        DisciplineValidator
                .getInstance()
                .add(requestDto,HttpStatus.CONFLICT);

        requestDto.setDiscipline(valueHolder);

    }

    @Test
    void T05_Find_All_Discipline_Should_Return_Status_OK_Containing_Added_Discipline(){

        boolean isPresent = DisciplineValidator
                .getInstance()
                .findAll(HttpStatus.OK)
                .stream()
                .map(DisciplineResponseDto::getDiscipline)
                .anyMatch(requestDto.getDiscipline()::equals);

        Assertions.assertTrue(isPresent);
    }

    @Test
    void T06_Find_Discipline_By_Name_ShouldReturn_Status_OK_Containing_Added_Discipline(){

        DisciplineResponseDto responseDto =

                DisciplineValidator
                        .getInstance()
                        .findByName(requestDto.getDiscipline(),HttpStatus.OK);

        Assertions.assertEquals(responseDto.getDiscipline(),responseDto.getDiscipline());

    }

    @Test
    void T07_Find_Discipline_By_Name_Not_Exist_ShouldReturn_Status_NOT_FOUND(){

        DisciplineValidator
                .getInstance()
                .findByName("NOT EXISTING DISCIPLINE",HttpStatus.NOT_FOUND);

    }

    @Test
    void T08_Exist_Discipline_By_Name_ShouldReturn_Status_OK(){

        DisciplineValidator
                .getInstance()
                .existDisciplineByName(requestDto.getDiscipline(),HttpStatus.OK);

    }

    @Test
    void T09_Exist_Discipline_By_Name_Not_Exist_ShouldReturn_Status_BAD_REQUEST(){

        DisciplineValidator
                .getInstance()
                .existDisciplineByName("NOT EXISTING DISCIPLINE",HttpStatus.BAD_REQUEST);

    }

    @Test
    void T10_deleteCreatedDiscipline_ShouldReturnStatus_OK(){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(requestDto.getDiscipline(),HttpStatus.OK);


    }

    @Test
    void T11_deleteCreatedDisciplineAgain_ShouldReturnStatus_NOTFOUND(){

        DisciplineValidator
                .getInstance()
                .deleteDisciplineByName(requestDto.getDiscipline(),HttpStatus.NOT_FOUND);

    }

}
