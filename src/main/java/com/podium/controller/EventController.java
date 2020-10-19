package com.podium.controller;

import com.podium.model.dto.request.EventRequestDto;
import com.podium.service.DisciplineService;
import com.podium.service.EventService;
import com.podium.service.GenderService;
import com.podium.service.UserService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventController {

    private EventService eventService;
    private DisciplineService disciplineService;
    private GenderService genderService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, DisciplineService disciplineService, GenderService genderService, UserService userService) {
        this.eventService = eventService;
        this.disciplineService = disciplineService;
        this.genderService = genderService;
        this.userService = userService;
    }

    @PostMapping("/event/add")
    public ResponseEntity addEvent(@RequestBody EventRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.eventService.existEventByTitle(requestDto.getTitle()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Event with given title already exists");

        if(!this.disciplineService.existByDisciplineName(requestDto.getDiscipline()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given discipline is not available");

        for(String gender : requestDto.getGenders())
            if(!this.genderService.existByGenderName(gender))
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "At least one of given genders is not available");

        if(!this.userService.existUserByUsername(requestDto.getAuthor()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given event author does not exist");


        this.eventService.addEvent(requestDto);

        return ResponseEntity.ok().build();


    }

}
