package com.podium.service;

import com.podium.controller.dto.response.WeekDayControllerResponse;
import com.podium.dal.entity.WeekDay;
import com.podium.dal.repository.WeekDayRepository;
import com.podium.service.exception.PodiumEntityNotFoundException;
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

    public WeekDay findWeekDay(String dayName) throws PodiumEntityNotFoundException {

        return this.weekDayRepository
                .findByDay(dayName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Week Day"));

    }

    private WeekDayControllerResponse convertEntityToResponseDto(WeekDay weekDay){

        return new WeekDayControllerResponse(weekDay.getDay());

    }

    public WeekDay getEntity(String dayName) throws PodiumEntityNotFoundException {

        return this.weekDayRepository
                .findByDay(dayName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Week Day"));

    }

}
