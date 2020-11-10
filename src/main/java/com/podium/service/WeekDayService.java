package com.podium.service;

import com.podium.model.dto.response.WeekDayResponse;
import com.podium.model.entity.time.WeekDay;
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

    public Iterable<WeekDayResponse> findAllWeekDay(){

        List<WeekDayResponse> responses = new ArrayList<>();

        this.weekDayRepository.findAll()
                .forEach(x -> responses
                        .add(this.convertEntityToResponseDto(x)));

        return responses;

    }

    private WeekDayResponse convertEntityToResponseDto(WeekDay weekDay){

        return new WeekDayResponse(weekDay.getDay());

    }



}
