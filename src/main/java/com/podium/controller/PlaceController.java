package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.EventRequestDto;
import com.podium.model.dto.request.PlaceRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceController {

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(@RequestBody PlaceRequestDto requestDto) {

        System.out.println(requestDto.getName());


        return ResponseEntity.ok().build();



    }



}
