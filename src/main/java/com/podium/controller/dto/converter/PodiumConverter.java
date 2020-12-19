package com.podium.controller.dto.converter;

import com.podium.controller.dto.other.*;
import com.podium.controller.dto.response.PlaceResponse;
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

public class PodiumConverter{

    private static PodiumConverter instance;

    private PodiumConverter() {}

    public static PodiumConverter getInstance() {
        if(instance == null) {
            instance = new PodiumConverter();
        }
        return instance;
    }

    public PlaceResponse convertPlaceToResponseDto(Place place){

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
                            convertResourcesToFiles(review.getImages()),
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
                convertResourcesToFiles(place.getPlaceImages()),
                placeReviews
        );




    }

    public ReviewResponse convertReviewToResponseDto(Review review){

        return new ReviewResponse(
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

    private Set<RatingDto> convertStarRatingsToRatingDtos(Set<StarRating> starRatings){

        var ratings = new HashSet<RatingDto>();

        starRatings.forEach(rating -> ratings.add(new RatingDto(
                rating.getCategory().getCategory(),
                rating.getRating()

        )));

        return ratings;

    }

    private List<PodiumFileDto> convertResourcesToFiles(Set<PodiumResource> resources){

        List<PodiumFileDto> podiumFileDtos = new ArrayList<>();

        resources.forEach(x -> {

                    if (x != null) {

                        try {
                            podiumFileDtos.add(new PodiumFileDto(
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

        return podiumFileDtos;

    }




}
