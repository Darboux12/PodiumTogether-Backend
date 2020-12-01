package com.podium.service.time;

import com.podium.model.dto.response.time.WeekDayResponseDto;
import com.podium.model.entity.time.WeekDay;
import com.podium.repository.time.WeekDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeekDayService {

    private WeekDayRepository weekDayRepository;

    @Autowired
    public WeekDayService(WeekDayRepository weekDayRepository) {
        this.weekDayRepository = weekDayRepository;
    }

    public Iterable<WeekDayResponseDto> findAllWeekDay(){

        List<WeekDayResponseDto> responses = new ArrayList<>();

        this.weekDayRepository.findAll()
                .forEach(x -> responses
                        .add(this.convertEntityToResponseDto(x)));

        return responses;

    }

    private WeekDayResponseDto convertEntityToResponseDto(WeekDay weekDay){

        return new WeekDayResponseDto(weekDay.getDay());

    }



}
