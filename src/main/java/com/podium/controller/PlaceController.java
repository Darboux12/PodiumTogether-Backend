package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.place.PlaceRequestDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.service.DisciplineService;
import com.podium.service.LocalizationService;
import com.podium.service.PlaceService;
import com.podium.service.RatingCategoryService;
import com.podium.controller.response.PodiumAlreadyExistWebException;
import com.podium.controller.response.PodiumNotExistWebException;
import com.podium.controller.response.PodiumTimeBeforeWebException;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
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
    private RatingCategoryService ratingCategoryService;
    private LocalizationService localizationService;
    private PodiumDtoValidator dtoValidator;

    public PlaceController(DisciplineService disciplineService, PlaceService placeService, RatingCategoryService ratingCategoryService, LocalizationService localizationService, PodiumDtoValidator dtoValidator) {
        this.disciplineService = disciplineService;
        this.placeService = placeService;
        this.ratingCategoryService = ratingCategoryService;
        this.localizationService = localizationService;
        this.dtoValidator = dtoValidator;
    }

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(@RequestBody PlaceRequestDto requestDto) {

        try {
            dtoValidator.validateRequestBody(requestDto);
        } catch (PodiumEmptyTextException e) {
            e.printStackTrace();
        }

        if(this.placeService.existByName(requestDto.getName()))
            throw new PodiumAlreadyExistWebException("Place with given name");

        if(!this.disciplineService.existByDisciplineName(requestDto.getDiscipline()))
            throw new PodiumNotExistWebException("Discipline");

        if(requestDto.getOpeningDays().size() != 7)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Opening days list must contains exactly 7 days");

        requestDto
                .getOpeningDays()
                .forEach(x -> {
                    if(x.getOpeningTimeFrom().isAfter(x.getOpeningTimeTo()))
                        throw new PodiumTimeBeforeWebException
                                ("OpeningTimeFrom","OpeningTimeTo");
                });

        requestDto
                .getRatings()
                .forEach(x -> {

                    if(!this.ratingCategoryService.existCategory(x.getCategory()))
                        throw new PodiumNotExistWebException("Rating Category");
                });

        if(this.localizationService.existLocalization(
                requestDto.getPlaceLocalization().getCity(),
                requestDto.getPlaceLocalization().getStreet(),
                requestDto.getPlaceLocalization().getBuildingNumber(),
                requestDto.getPlaceLocalization().getPostalCode()
        ))
            throw new PodiumAlreadyExistWebException("Place with given localization");

        return ResponseEntity.ok().build();

    }

}
