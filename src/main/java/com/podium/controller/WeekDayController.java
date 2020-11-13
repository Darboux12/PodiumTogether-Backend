package com.podium.controller;

import com.podium.model.dto.response.WeekDayResponseDto;
import com.podium.service.WeekDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeekDayController {

    private WeekDayService weekDayService;

    @Autowired
    public WeekDayController(WeekDayService weekDayService) {
        this.weekDayService = weekDayService;
    }

    @GetMapping("/weekday/find/all")
    public ResponseEntity<Iterable<WeekDayResponseDto>> findAllWeekDay(){

        return ResponseEntity
                .ok()
                .body(this.weekDayService.findAllWeekDay());

    }

}
