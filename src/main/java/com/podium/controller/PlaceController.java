package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.PlaceRequestDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class PlaceController {

    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(@RequestBody @PodiumValidBody PlaceRequestDto requestDto) {

        return ResponseEntity.ok().build();

    }




}
