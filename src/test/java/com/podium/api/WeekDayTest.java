package com.podium.api;

import com.podium.model.dto.response.WeekDayResponseDto;
import com.podium.validator.WeekDayValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.stream.Collectors;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class WeekDayTest {

    @Test
    void Find_All_Week_Day_Containing_All_Week_Days(){

        List<String> weekDays = List.of("Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        );

        List<String> responseDtos =
                WeekDayValidator
                        .getInstance()
                        .findAll().stream().map(WeekDayResponseDto::getDay)
                        .collect(Collectors.toList());

        Assertions.assertTrue(weekDays.containsAll(responseDtos));

    }
}
