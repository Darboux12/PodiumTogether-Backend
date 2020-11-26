package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.PlaceRequestDto;
import com.podium.service.DisciplineService;
import com.podium.service.PlaceService;
import com.podium.validation.exception.PodiumAlreadyExistException;
import com.podium.validation.exception.PodiumNotExistException;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PlaceController {

    private DisciplineService disciplineService;
    private PlaceService placeService;

    public PlaceController(DisciplineService disciplineService, PlaceService placeService) {
        this.disciplineService = disciplineService;
        this.placeService = placeService;
    }

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(@RequestBody PlaceRequestDto requestDto) {

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.placeService.existByName(requestDto.getName()))
            throw new PodiumAlreadyExistException("City");

        if(!this.disciplineService.existByDisciplineName(requestDto.getDiscipline()))
            throw new PodiumNotExistException("Discipline");

        if(requestDto.getOpeningDays().size() != 7)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Opening days list must contains exactly 7 days");


        return ResponseEntity.ok().build();



    }



}
