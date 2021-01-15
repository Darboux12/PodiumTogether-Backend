package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
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
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(
            @RequestPart("place") @PodiumValidBody PlaceAddControllerRequest requestDto,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("documents") List<MultipartFile> documents) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError {

        this.placeService.addPlace(this.convertAddRequestToServiceDto(requestDto,images,documents));

        return ResponseEntity.ok().build();

    }

    @GetMapping(PodiumEndpoint.findPlaceByName)
    public ResponseEntity<PlaceControllerResponse> findPlaceByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        var place = this.placeService.findPlaceByName(name);

        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertPlaceServiceDtoToControllerResponseDto(place));
    }

    @GetMapping(PodiumEndpoint.findPlaceById)
    public ResponseEntity<PlaceControllerResponse> findPlaceById(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        var place = this.placeService.findPlaceById(id);

        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertPlaceServiceDtoToControllerResponseDto(place));
    }

    @GetMapping(PodiumEndpoint.findAllPlaces)
    public ResponseEntity<Iterable<PlaceControllerResponse>> findAllPlaces() throws PodiumEntityNotFoundException {

        var places = this.placeService.findAllPlace();

        return ResponseEntity
                .ok()
                .body(ControllerResponseConverter.
                        getInstance()
                        .convertPlaceServiceIterableToResponseDto(places));


    }

    @DeleteMapping(PodiumEndpoint.deletePlaceById)
    public ResponseEntity deletePlaceById(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        this.placeService.deletePlaceById(id);

        return ResponseEntity.ok().body("Place successfully deleted");
    }

    private PlaceAddServiceRequest convertAddRequestToServiceDto(PlaceAddControllerRequest addRequest, List<MultipartFile> images, List<MultipartFile> documents){

        LocalizationServiceDto localizationServiceDto =

                new LocalizationServiceDto(
                        addRequest.getLocalizationControllerDto().getCity(),
                        addRequest.getLocalizationControllerDto().getStreet(),
                        addRequest.getLocalizationControllerDto().getBuildingNumber(),
                        addRequest.getLocalizationControllerDto().getPostalCode(),
                        addRequest.getLocalizationControllerDto().getLocalizationRemarks()
                );

        var businessDayServiceDtos = new HashSet<BusinessDayServiceDto>();

        addRequest.getBusinessDayControllerDtos()
                .forEach(day -> businessDayServiceDtos
                        .add(new BusinessDayServiceDto(
                day.getDay(),
                day.isOpen(),
                day.getOpeningTimeFrom(),
                day.getOpeningTimeTo()
        )));

        return new PlaceAddServiceRequest(
                addRequest.getName(),
                addRequest.getDiscipline(),
                localizationServiceDto,
                businessDayServiceDtos,
                addRequest.getCost(),
                addRequest.getUsageTime(),
                addRequest.getMinAge(),
                addRequest.getMaxAge(),
                new HashSet<>(images),
                new HashSet<>(documents)
        );

    }

}
