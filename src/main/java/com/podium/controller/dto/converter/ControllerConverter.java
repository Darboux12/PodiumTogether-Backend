package com.podium.controller.dto.converter;

import com.podium.controller.dto.other.*;
import com.podium.controller.dto.response.PlaceControllerResponse;
import com.podium.controller.dto.response.ReviewControllerResponse;
import com.podium.dal.entity.Place;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.Review;
import com.podium.dal.entity.StarRating;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControllerConverter {

    private static ControllerConverter instance;

    private ControllerConverter() {}

    public static ControllerConverter getInstance() {
        if(instance == null) {
            instance = new ControllerConverter();
        }
        return instance;
    }

    public PlaceControllerResponse convertPlaceToResponseDto(Place place){

        var placeLocalization = new LocalizationControllerDto(
                place.getPlaceLocalization().getCity().getCity(),
                place.getPlaceLocalization().getStreet().getStreet(),
                place.getPlaceLocalization().getBuildingNumber(),
                place.getPlaceLocalization().getPostalCode(),
                place.getPlaceLocalization().getRemarks()
        );

        var businessDaysPlace = new ArrayList<BusinessDayControllerDto>();

        place
                .getBusinessDays()
                .forEach(day -> businessDaysPlace.add(new BusinessDayControllerDto(
                        day.getDay().getDay(),
                        day.isOpen(),
                        day.getOpenTimeFrom(),
                        day.getOpenTimeTo()
                )));

        var placeReviews = new HashSet<ReviewControllerResponse>();

        place
                .getReviews()
                .forEach(review -> {

                    var ratings = new HashSet<RatingControllerDto>();

                    review
                            .getStarRatings()
                            .forEach(rating -> {

                                ratings.add(new RatingControllerDto
                                        (rating.getCategory().getCategory(),rating.getRating()));

                            });

                    placeReviews.add(new ReviewControllerResponse(
                            review.getId(),
                            ratings,
                            review.getOpinion(),
                            review.getAuthor().getUsername(),
                            review.getPlace().getName(),
                            convertResourcesToFiles(review.getImages()),
                            review.getLikes(),
                            review.getDislikes()
                    ));

                });


        return new PlaceControllerResponse(
                place.getId(),
                place.getName(),
                place.getDiscipline().getDiscipline(),
                placeLocalization,
                businessDaysPlace,
                place.getCost(),
                place.getUsageTime(),
                place.getMinAge(),
                place.getMaxAge(),
                convertResourcesToFiles(place.getPlaceResources()),
                placeReviews
        );




    }

    public Iterable<PlaceControllerResponse> convertPlaceIterableToResponseDto(Iterable<Place> places){

        var placeResponses = new HashSet<PlaceControllerResponse>();

        places.forEach(x -> placeResponses.add(this.convertPlaceToResponseDto(x)));

        return placeResponses;

    }

    public ReviewControllerResponse convertReviewToResponseDto(Review review){

        return new ReviewControllerResponse(
                review.getId(),
                this.convertStarRatingsToRatingDtos(review.getStarRatings()),
                review.getOpinion(),
                review.getAuthor().getUsername(),
                review.getPlace().getName(),
                this.convertResourcesToFiles(review.getImages()),
                review.getLikes(),
                review.getDislikes()

        );
    }

    private Set<RatingControllerDto> convertStarRatingsToRatingDtos(Set<StarRating> starRatings){

        var ratings = new HashSet<RatingControllerDto>();

        starRatings.forEach(rating -> ratings.add(new RatingControllerDto(
                rating.getCategory().getCategory(),
                rating.getRating()

        )));

        return ratings;

    }

    private List<FileControllerDto> convertResourcesToFiles(Set<PodiumResource> resources){

        List<FileControllerDto> fileControllerDtos = new ArrayList<>();

        resources.forEach(x -> {

                    if (x != null) {

                        try {
                            fileControllerDtos.add(new FileControllerDto(
                                    x.getName(),
                                    x.getType(),
                                    FileCopyUtils.copyToByteArray(new File(x.getPath()))
                            ));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

        );

        return fileControllerDtos;

    }




}
