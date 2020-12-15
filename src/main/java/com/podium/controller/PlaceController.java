package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
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
            @RequestPart("images") List<MultipartFile> images) {

        this.placeService.addPlace(this.convertAddRequestToServiceDto(requestDto,images));

        return ResponseEntity.ok().build();

    }

    @GetMapping(PodiumEndpoint.findPlaceByName)
    public ResponseEntity<PlaceResponse> findPlaceByName(@PathVariable @PodiumValidVariable String name){

        var place = this.placeService.findPlaceByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(place));
    }

    @DeleteMapping(PodiumEndpoint.deletePlaceById)
    public ResponseEntity deletePlaceById(@PathVariable @PodiumValidVariable int id){

        this.placeService.deletePlaceById(id);

        return ResponseEntity.ok().body("Place successfully deleted");
    }


    private PlaceAddServiceDto convertAddRequestToServiceDto(PlaceAddRequest addRequest,List<MultipartFile> images){

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
                new HashSet<>(images)
        );

    }

    private PlaceResponse convertEntityToResponseDto(Place place){


        var placeLocalization = new LocalizationDto(
                place.getPlaceLocalization().getCity().getCity(),
                place.getPlaceLocalization().getStreet().getStreet(),
                place.getPlaceLocalization().getBuildingNumber(),
                place.getPlaceLocalization().getPostalCode(),
                place.getPlaceLocalization().getRemarks()
        );

        var businessDaysPlace = new ArrayList<BusinessDayDto>();

        place
                .getBusinessDays()
                .forEach(day -> businessDaysPlace.add(new BusinessDayDto(
                        day.getDay().getDay(),
                        day.isOpen(),
                        day.getOpenTimeFrom(),
                        day.getOpenTimeTo()
                )));

        var placeReviews = new HashSet<ReviewResponse>();

        place
                .getReviews()
                .forEach(review -> {

                    var ratings = new HashSet<RatingDto>();

                    review
                            .getStarRatings()
                            .forEach(rating -> {

                                ratings.add(new RatingDto
                                        (rating.getCategory().getCategory(),rating.getRating()));

                            });

                    placeReviews.add(new ReviewResponse(
                            review.getId(),
                            ratings,
                            review.getOpinion(),
                            review.getAuthor().getUsername(),
                            review.getPlace().getName(),
                            this.findPlaceFiles(review.getImages()),
                            review.getLikes(),
                            review.getDislikes()
                    ));

                });


        return new PlaceResponse(
                place.getId(),
                place.getName(),
                place.getDiscipline().getDiscipline(),
                placeLocalization,
                businessDaysPlace,
                place.getCost(),
                place.getUsageTime(),
                place.getMinAge(),
                place.getMaxAge(),
                this.findPlaceFiles(place.getPlaceImages()),
                placeReviews
        );

    }

    private List<PodiumFileDto> findPlaceFiles(Set<PodiumResource> resources){

        List<PodiumFileDto> podiumFileDtos = new ArrayList<>();

        resources.forEach(x -> {

                    try {
                        podiumFileDtos.add(new PodiumFileDto(
                                x.getName(),
                                x.getType(),
                                FileCopyUtils.copyToByteArray(new File(x.getPath()))
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return podiumFileDtos;

    }

}
