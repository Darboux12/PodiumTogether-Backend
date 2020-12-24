package com.podium.controller.dto.converter;

import com.podium.controller.dto.other.*;
import com.podium.controller.dto.response.PlaceControllerResponse;
import com.podium.controller.dto.response.ReviewControllerResponse;
import com.podium.dal.entity.*;
import com.podium.service.dto.other.*;
import com.podium.service.dto.response.PlaceServiceResponse;

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

    public PlaceControllerResponse convertPlaceServiceDtoToControllerResponseDto(PlaceServiceResponse place){


        return new PlaceControllerResponse(
                place.getId(),
                place.getName(),
                place.getDiscipline(),
                this.convertLocalizationServiceDtoToControllerDto(place.getLocalizationDto()),
                this.convertBusinessDaysServiceDtoToControllerDto(place.getBusinessDayDtos()),
                place.getCost(),
                place.getUsageTime(),
                place.getMinAge(),
                place.getMaxAge(),
                this.convertImageFilesServiceToControllerFiles(place.getImages()),
                this.convertDocumentFilesServiceToControllerFiles(place.getDocuments()),
                this.convertReviewServiceIterableToControllerResponse(place.getReviews())
        );




    }

    public Iterable<PlaceControllerResponse> convertPlaceServiceIterableToResponseDto(Iterable<PlaceServiceResponse> places){

        var placeResponses = new HashSet<PlaceControllerResponse>();

        places.forEach(x -> placeResponses.add(this.convertPlaceServiceDtoToControllerResponseDto(x)));

        return placeResponses;

    }

    public Iterable<ReviewControllerResponse> convertReviewServiceIterableToResponseDto(Iterable<ReviewServiceDto> reviews){

        var reviewResponses = new HashSet<ReviewControllerResponse>();

        reviews.forEach(x -> reviewResponses.add(this.convertReviewServiceDtoToResponseDto(x)));

        return reviewResponses;

    }

    public ReviewControllerResponse convertReviewServiceDtoToResponseDto(ReviewServiceDto review){

        return new ReviewControllerResponse(
                review.getId(),
                this.convertStarRatingsToRatingDtos(review.getStarRatings()),
                review.getOpinion(),
                review.getAuthor(),
                review.getPlace(),
                this.convertImageFilesServiceToControllerFiles(review.getImages()),
                review.getLikes(),
                review.getDislikes()

        );
    }

    private Set<StarRatingControllerDto> convertStarRatingsToRatingDtos(Set<StarRatingServiceDto> starRatings){

        var ratings = new HashSet<StarRatingControllerDto>();

        starRatings.forEach(rating -> ratings.add(new StarRatingControllerDto(
                rating.getCategory(),
                rating.getRating()

        )));

        return ratings;

    }

    private Set<ReviewControllerResponse> convertReviewServiceIterableToControllerResponse(Set<ReviewServiceDto> reviews){

        var placeReviews = new HashSet<ReviewControllerResponse>();

        reviews.forEach(review -> {

            var ratings = new HashSet<StarRatingControllerDto>();

            review
                    .getStarRatings()
                    .forEach(rating -> ratings.add(new StarRatingControllerDto(rating.getCategory(),rating.getRating())));

            placeReviews.add(new ReviewControllerResponse(
                    review.getId(),
                    ratings,
                    review.getOpinion(),
                    review.getAuthor(),
                    review.getPlace(),
                    this.convertImageFilesServiceToControllerFiles(review.getImages()),
                    review.getLikes(),
                    review.getDislikes()
            ));

        });


        return placeReviews;

    }

    private LocalizationControllerDto convertLocalizationServiceDtoToControllerDto(LocalizationServiceDto localization){

        return new LocalizationControllerDto(
                localization.getCity(),
                localization.getStreet(),
                localization.getBuildingNumber(),
                localization.getPostalCode(),
                localization.getLocalizationRemarks()
        );




    }

    private Set<BusinessDayControllerDto> convertBusinessDaysServiceDtoToControllerDto(Set<BusinessDayServiceDto> businessDays){

        var businessDaysPlace = new HashSet<BusinessDayControllerDto>();

        businessDays
                .forEach(day -> businessDaysPlace.add(new BusinessDayControllerDto(
                        day.getDay(),
                        day.isOpen(),
                        day.getOpeningTimeFrom(),
                        day.getOpeningTimeTo()
                )));

        return businessDaysPlace;
    }

    private Set<FileControllerDto> convertDocumentFilesServiceToControllerFiles(Set<FileServiceDto> documents){

        var fileDtos = new HashSet<FileControllerDto>();

        documents.forEach(x -> {

                    if (x != null && this.isAcceptedDocumentType(x.getType())) {

                        fileDtos.add(new FileControllerDto(
                                x.getName(),
                                x.getType(),
                                x.getContent()
                        ));

                    }
                }
        );

        return fileDtos;

    }

    private Set<FileControllerDto> convertImageFilesServiceToControllerFiles(Set<FileServiceDto> images){

        var fileDtos = new HashSet<FileControllerDto>();

        images.forEach(x -> {

                    if (x != null && this.isAcceptedImagesType(x.getType())) {

                        fileDtos.add(new FileControllerDto(
                                x.getName(),
                                x.getType(),
                                x.getContent()
                        ));

                    }
                }
        );

        return fileDtos;

    }

    private List<String> getAcceptedImagesTypes(){

        return List.of(
                "image/jpeg",
                "image/jpg",
                "image/png",
                "image/tiff",
                "image/gif",
                "image/raw",
                "image/bmp",
                "image/webp"
        );

    }

    private List<String> getAcceptedDocumentsTypes(){

        return List.of(
                "text/plain",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.oasis.opendocument.text",
                "application/pdf"
        );

    }

    private boolean isAcceptedImagesType(String imageType){
        return this.getAcceptedImagesTypes().contains(imageType);
    }

    private boolean isAcceptedDocumentType(String documentType){
        return this.getAcceptedDocumentsTypes().contains(documentType);
    }







}
