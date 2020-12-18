package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.PodiumConverter;
import com.podium.controller.dto.other.*;
import com.podium.controller.dto.request.PlaceAddRequest;
import com.podium.controller.dto.response.PlaceResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Place;
import com.podium.dal.entity.PodiumResource;
import com.podium.service.BusinessDayService;
import com.podium.service.PlaceService;
import com.podium.service.dto.BusinessDayServiceDto;
import com.podium.service.dto.LocalizationServiceDto;
import com.podium.service.dto.PlaceAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
            @RequestPart("place") @PodiumValidBody PlaceAddRequest requestDto,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("documents") List<MultipartFile> documents) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError {

        this.placeService.addPlace(this.convertAddRequestToServiceDto(requestDto,images,documents));

        return ResponseEntity.ok().build();

    }

    @GetMapping(PodiumEndpoint.findPlaceByName)
    public ResponseEntity<PlaceResponse> findPlaceByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        var place = this.placeService.findPlaceByName(name);

        return ResponseEntity.ok().body(PodiumConverter.getInstance().convertPlaceToResponseDto(place));
    }

    @DeleteMapping(PodiumEndpoint.deletePlaceById)
    public ResponseEntity deletePlaceById(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        this.placeService.deletePlaceById(id);

        return ResponseEntity.ok().body("Place successfully deleted");
    }

    private PlaceAddServiceDto convertAddRequestToServiceDto(PlaceAddRequest addRequest,List<MultipartFile> images,List<MultipartFile> documents){

        LocalizationServiceDto localizationServiceDto =

                new LocalizationServiceDto(
                        addRequest.getLocalizationDto().getCity(),
                        addRequest.getLocalizationDto().getStreet(),
                        addRequest.getLocalizationDto().getBuildingNumber(),
                        addRequest.getLocalizationDto().getPostalCode(),
                        addRequest.getLocalizationDto().getLocalizationRemarks()
                );

        var businessDayServiceDtos = new HashSet<BusinessDayServiceDto>();

        addRequest.getBusinessDayDtos()
                .forEach(day -> businessDayServiceDtos
                        .add(new BusinessDayServiceDto(
                day.getDay(),
                day.isOpen(),
                day.getOpeningTimeFrom(),
                day.getOpeningTimeTo()
        )));

        return new PlaceAddServiceDto(
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
