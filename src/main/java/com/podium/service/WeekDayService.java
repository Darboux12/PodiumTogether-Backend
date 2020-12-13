package com.podium.service;

import com.podium.controller.dto.response.WeekDayResponse;
import com.podium.dal.entity.WeekDay;
import com.podium.dal.repository.WeekDayRepository;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeekDayService {

    private WeekDayRepository weekDayRepository;

    public WeekDayService(WeekDayRepository weekDayRepository) {
        this.weekDayRepository = weekDayRepository;
    }

    public Iterable<WeekDay> findAllWeekDay(){
        return this.weekDayRepository.findAll();
    }

    public WeekDay findWeekDay(String dayName){

        return this.weekDayRepository
                .findByDay(dayName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Week Day"));

    }

    private WeekDayResponse convertEntityToResponseDto(WeekDay weekDay){

        return new WeekDayResponse(weekDay.getDay());

    }

    public WeekDay getEntity(String dayName){

        return this.weekDayRepository
                .findByDay(dayName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Week Day"));

    }

}
