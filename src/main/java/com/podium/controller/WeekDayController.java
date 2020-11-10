package com.podium.controller;

import com.podium.model.dto.response.WeekDayResponse;
import com.podium.service.WeekDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeekDayController {

    private WeekDayService weekDayService;

    @Autowired
    public WeekDayController(WeekDayService weekDayService) {
        this.weekDayService = weekDayService;
    }

    @GetMapping("/weekday/find/all")
    public ResponseEntity<Iterable<WeekDayResponse>> findAllWeekDay(){

        return ResponseEntity
                .ok()
                .body(this.weekDayService.findAllWeekDay());

    }


}
