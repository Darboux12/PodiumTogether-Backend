package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.PlaceRequestDto;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PlaceController {

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(@Valid @RequestBody PlaceRequestDto requestDto) {

        PodiumValidator
                .getInstance()
                .validateRequestBody(requestDto);


        return ResponseEntity.ok().build();



    }



}
