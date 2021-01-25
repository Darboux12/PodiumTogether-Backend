package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.PlaceAddControllerRequest;
import com.podium.controller.dto.response.PlaceControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.BusinessDayService;
import com.podium.service.PlaceService;
import com.podium.service.dto.other.BusinessDayServiceDto;
import com.podium.service.dto.other.LocalizationServiceDto;
import com.podium.service.dto.request.PlaceAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class PlaceController {

    private PlaceService placeService;

    public PlaceController(PlaceService placeService, BusinessDayService businessDayService) {
        this.placeService = placeService;
    }

    // SUBSCRIBER
    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(
            @RequestPart("place") @PodiumValidBody PlaceAddControllerRequest requestDto,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("documents") List<MultipartFile> documents,
            Authentication authentication) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError, PodiumAuthorityException {
        this.placeService.addPlace(ControllerRequestConverter.getInstance().convertPlaceAddRequestToServiceDto(requestDto,images,documents),authentication.getName());
        return ResponseEntity.ok().build();
    }

    // SUBSCRIBER
    @GetMapping(PodiumEndpoint.findPlaceByName)
    public ResponseEntity<PlaceControllerResponse> findPlaceByName(@PathVariable @PodiumValidVariable String name,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        var place = this.placeService.findPlaceByName(name,authentication.getName());
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertPlaceServiceDtoToControllerResponseDto(place));
    }

    // SUBSCRIBER
    @GetMapping(PodiumEndpoint.findPlaceById)
    public ResponseEntity<PlaceControllerResponse> findPlaceById(@PathVariable @PodiumValidVariable int id,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        var place = this.placeService.findPlaceById(id,authentication.getName());
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertPlaceServiceDtoToControllerResponseDto(place));
    }

    // SUBSCRIBER
    @GetMapping(PodiumEndpoint.findAllPlaces)
    public ResponseEntity<Iterable<PlaceControllerResponse>> findAllPlaces(Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        var places = this.placeService.findAllPlace(authentication.getName());
        return ResponseEntity
                .ok()
                .body(ControllerResponseConverter.
                        getInstance()
                        .convertPlaceServiceIterableToResponseDto(places));
    }

    // ADMIN OR MODERATOR
    @DeleteMapping(PodiumEndpoint.deletePlaceById)
    public ResponseEntity deletePlaceById(@PathVariable @PodiumValidVariable int id,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        this.placeService.deletePlaceById(id,authentication.getName());
        return ResponseEntity.ok().body("Place successfully deleted");
    }

}
