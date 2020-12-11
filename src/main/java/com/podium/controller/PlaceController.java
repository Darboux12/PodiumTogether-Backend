package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.PlaceAddRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
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
    public ResponseEntity addPlace(@RequestBody @PodiumValidBody PlaceAddRequest requestDto) {

        return ResponseEntity.ok().build();

    }




}
