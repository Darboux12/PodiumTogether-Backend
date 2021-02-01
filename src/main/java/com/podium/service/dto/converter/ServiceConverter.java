package com.podium.service.dto.converter;

import com.podium.dal.entity.*;
import com.podium.service.dto.other.*;
import com.podium.service.dto.response.EventServiceResponse;
import com.podium.service.dto.response.PlaceServiceResponse;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceConverter {

    private static ServiceConverter instance;

    private ServiceConverter() {}

    public static ServiceConverter getInstance() {
        if(instance == null) {
            instance = new ServiceConverter();
        }
        return instance;
    }

    public PlaceServiceResponse convertPlaceToResponseDto(Place place){

        return new PlaceServiceResponse(
                place.getId(),
                place.getName(),
                place.getDiscipline().getDiscipline(),
                this.convertLocalizationToDto(place.getPlaceLocalization()),
                this.convertBusinessDaysToDto(place.getBusinessDays()),
                place.getCost(),
                place.getUsageTime(),
                place.getMinAge(),
                place.getMaxAge(),
                place.getAuthor().getUsername(),
                this.convertImagesResourcesToFiles(place.getPlaceResources()),
                this.convertDocumentsResourcesToFiles(place.getPlaceResources()),
                this.convertReviewIterableToDto(place.getReviews())
        );

    }

    public EventServiceResponse convertEventToResponseDto(Event event){

        Set<String> users = new HashSet<>();

        event.getUsersJoined().forEach(x -> users.add(x.getUsername()));

        return new EventServiceResponse(
                event.getId(),
                event.getTitle(),
                event.getDateFrom(),
                event.getDateTo(),
                event.getPeopleNumber(),
                event.getMinAge(),
                event.getMaxAge(),
                event.getDescription(),
                users,
                event.getAuthor().getUsername(),
                event.getDiscipline().getDiscipline(),
                event.getViews(),
                this.convertImagesResourcesToFiles(event.getEventResources()),
                this.convertDocumentsResourcesToFiles(event.getEventResources()),
                event.getCreationDate(),
                event.getPlace().getName()
        );

    }

    public Iterable<PlaceServiceResponse> convertPlaceIterableToResponseDto(Iterable<Place> places){

        var responses = new HashSet<PlaceServiceResponse>();

        places.forEach(place -> responses.add(this.convertPlaceToResponseDto(place)));

        return responses;
    }

    public Set<ReviewServiceDto> convertReviewIterableToDto(Iterable<Review> reviews){

        var placeReviews = new HashSet<ReviewServiceDto>();

        reviews.forEach(review -> {

            var ratings = new HashSet<StarRatingServiceDto>();

            review
                    .getStarRatings()
                    .forEach(rating -> ratings.add(new StarRatingServiceDto(rating.getCategory().getCategory(),rating.getRating())));

            placeReviews.add(new ReviewServiceDto(
                            review.getId(),
                            ratings,
                            review.getOpinion(),
                            review.getAuthor().getUsername(),
                            review.getPlace().getName(),
                            this.convertImagesResourcesToFiles(review.getImages()),
                            review.getLikes(),
                            review.getDislikes(),
                            review.getDate()
                    ));

                });


        return placeReviews;

    }

    private LocalizationServiceDto convertLocalizationToDto(Localization localization){

        return new LocalizationServiceDto(
                localization.getCity().getCity(),
                localization.getStreet().getStreet(),
                localization.getBuildingNumber(),
                localization.getPostalCode(),
                localization.getRemarks()
        );




    }

    private Set<BusinessDayServiceDto> convertBusinessDaysToDto(Set<BusinessDay> businessDays){

        var businessDaysPlace = new HashSet<BusinessDayServiceDto>();

        businessDays
                .forEach(day -> businessDaysPlace.add(new BusinessDayServiceDto(
                        day.getDay().getDay(),
                        day.isOpen(),
                        day.getOpenTimeFrom(),
                        day.getOpenTimeTo()
                )));

        return businessDaysPlace;
    }

    private Set<FileServiceDto> convertDocumentsResourcesToFiles(Set<PodiumResource> resources){

        var fileDtos = new HashSet<FileServiceDto>();

        resources.forEach(x -> {

                    if (x != null && this.isAcceptedDocumentType(x.getType())) {

                        try {
                            fileDtos.add(new FileServiceDto(
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

        return fileDtos;

    }

    private Set<FileServiceDto> convertImagesResourcesToFiles(Set<PodiumResource> resources){

        var fileDtos = new HashSet<FileServiceDto>();

        resources.forEach(x -> {

                    if (x != null && this.isAcceptedImagesType(x.getType())) {

                        try {
                            fileDtos.add(new FileServiceDto(
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
