package com.podium.controller.place;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.place.PlaceRequestDto;
import com.podium.service.discipline.DisciplineService;
import com.podium.service.localization.LocalizationService;
import com.podium.service.place.PlaceService;
import com.podium.service.place.RatingCategoryService;
import com.podium.validation.exception.PodiumAlreadyExistException;
import com.podium.validation.exception.PodiumNotExistException;
import com.podium.validation.exception.PodiumTimeBeforeException;
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
    private RatingCategoryService ratingCategoryService;
    private LocalizationService localizationService;

    public PlaceController(DisciplineService disciplineService, PlaceService placeService, RatingCategoryService ratingCategoryService, LocalizationService localizationService) {
        this.disciplineService = disciplineService;
        this.placeService = placeService;
        this.ratingCategoryService = ratingCategoryService;
        this.localizationService = localizationService;
    }

    @PostMapping(PodiumEndpoint.addPlace)
    public ResponseEntity addPlace(@RequestBody PlaceRequestDto requestDto) {

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.placeService.existByName(requestDto.getName()))
            throw new PodiumAlreadyExistException("Place with given name");

        if(!this.disciplineService.existByDisciplineName(requestDto.getDiscipline()))
            throw new PodiumNotExistException("Discipline");

        if(requestDto.getOpeningDays().size() != 7)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Opening days list must contains exactly 7 days");

        requestDto
                .getOpeningDays()
                .forEach(x -> {
                    if(x.getOpeningTimeFrom().isAfter(x.getOpeningTimeTo()))
                        throw new PodiumTimeBeforeException
                                ("OpeningTimeFrom","OpeningTimeTo");
                });

        requestDto
                .getRatings()
                .forEach(x -> {

                    if(!this.ratingCategoryService.existCategory(x.getCategory()))
                        throw new PodiumNotExistException("Rating Category");
                });

        if(this.localizationService.existLocalization(
                requestDto.getPlaceLocalization().getCity(),
                requestDto.getPlaceLocalization().getStreet(),
                requestDto.getPlaceLocalization().getBuildingNumber(),
                requestDto.getPlaceLocalization().getPostalCode()
        ))
            throw new PodiumAlreadyExistException("Place with given localization");

        return ResponseEntity.ok().build();

    }

}
