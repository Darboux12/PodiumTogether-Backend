package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.EventAddControllerRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.EventService;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(PodiumEndpoint.addEvent)
    public ResponseEntity addEvent(
            @RequestPart("event") @PodiumValidBody EventAddControllerRequest requestDto,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("documents") List<MultipartFile> documents,
            Authentication authentication) throws PodiumEntityTimeConsistencyError, PodiumAuthorityException, PodiumEntityNotFoundException, PodiumEntityAlreadyExistException {
        this.eventService.addEvent(ControllerRequestConverter.getInstance().convertEventAddRequestToServiceDto(requestDto,images,documents),authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping(PodiumEndpoint.findEventByTitle)
    public ResponseEntity findEventByTitle(@PathVariable String title,Authentication authentication) throws PodiumAuthorityException, PodiumEntityNotFoundException {
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertEventServiceDtoToControllerResponseDto(this.eventService.findEventByTitle(title,authentication.getName())));
    }


    @DeleteMapping(PodiumEndpoint.deleteEventById)
    public ResponseEntity deleteEventById(@PathVariable int id,Authentication authentication) throws PodiumAuthorityException, PodiumEntityNotFoundException {
        this.eventService.deleteEventById(id,authentication.getName());
        return ResponseEntity.ok().build();
    }

}
