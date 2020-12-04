package com.podium.service;

import com.podium.model.dto.response.WeekDayResponseDto;
import com.podium.model.entity.WeekDay;
import com.podium.repository.WeekDayRepository;
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
