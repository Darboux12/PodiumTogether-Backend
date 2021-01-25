package com.podium.controller.dto.converter;

import com.podium.controller.dto.other.*;
import com.podium.controller.dto.response.*;
import com.podium.dal.entity.*;
import com.podium.service.dto.other.*;
import com.podium.service.dto.response.PlaceServiceResponse;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ControllerResponseConverter {

    private static ControllerResponseConverter instance;

    private ControllerResponseConverter() {}

    public static ControllerResponseConverter getInstance() {
        if(instance == null) {
            instance = new ControllerResponseConverter();
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

    public CityControllerResponse convertCityEntityToResponseDto(City city){
        return new CityControllerResponse(city.getCity());
    }

    public Iterable<CityControllerResponse> convertCityEntityIterableToResponseDto(Iterable<City> cities){

        var cityResponses = new ArrayList<CityControllerResponse>();

        cities.forEach(x -> cityResponses.add(this.convertCityEntityToResponseDto(x)));

        return cityResponses;
    }

    public ContactControllerResponse convertContactEntityToResponseDto(Contact contact){

        return new ContactControllerResponse(
                contact.getId(),
                contact.getUserEmail(),
                contact.getMessage(),
                contact.getSubject().getSubject()
        );

    }

    public Iterable<ContactControllerResponse> convertContactEntityIterableToResponseDto(Iterable<Contact> contacts){

        var contactResponses = new ArrayList<ContactControllerResponse>();

        contacts.forEach(x -> contactResponses.add(this.convertContactEntityToResponseDto(x)));

        return contactResponses;
    }

    public CountryControllerResponse convertCountryEntityToResponseDto(Country country){

        return new CountryControllerResponse(
                country.getCountryId(),
                country.getName(),
                country.getPrintableName(),
                country.getIso3(),
                country.getNumCode()
        );

    }

    public Iterable<CountryControllerResponse> convertCountryEntityIterableToResponseDto(Iterable<Country> countries){

        var countryResponses = new ArrayList<CountryControllerResponse>();

        countries.forEach(x -> countryResponses.add(this.convertCountryEntityToResponseDto(x)));

        return countryResponses;
    }

    public DisciplineControllerResponse convertDisciplineEntityToResponseDto(Discipline discipline){
        return new DisciplineControllerResponse(discipline.getDiscipline());
    }

    public Iterable<DisciplineControllerResponse> convertDisciplineEntityIterableToResponseDto(Iterable<Discipline> disciplines){

        var disciplineResponses = new ArrayList<DisciplineControllerResponse>();

        disciplines.forEach(x -> disciplineResponses.add(this.convertDisciplineEntityToResponseDto(x)));

        return disciplineResponses;
    }

    public GenderControllerResponse convertGenderEntityToResponseDto(Gender gender){
        return new GenderControllerResponse(gender.getGender());
    }

    public Iterable<GenderControllerResponse> convertGenderEntityIterableToResponseDto(Iterable<Gender> genders){

        var genderResponses = new ArrayList<GenderControllerResponse>();

        genders.forEach(x -> genderResponses.add(this.convertGenderEntityToResponseDto(x)));

        return genderResponses;
    }

    public NewsControllerResponse convertNewsEntityToResponseDto(News news){

        return new NewsControllerResponse(
                news.getId(),
                news.getTitle(),
                news.getShortText(),
                news.getText(),
                news.getLinkText(),
                news.getDate(),
                this.findNewsFiles(news)
        );

    }

    public Iterable<NewsControllerResponse> convertNewsEntityIterableToResponseDto(Iterable<News> contacts){

        var newsResponses = new ArrayList<NewsControllerResponse>();

        contacts.forEach(x -> newsResponses.add(this.convertNewsEntityToResponseDto(x)));

        return newsResponses;
    }

    public RatingCategoryControllerResponse convertRatingCategoryEntityToResponseDto(RatingCategory ratingCategory){
        return new RatingCategoryControllerResponse(ratingCategory.getCategory());
    }

    public Iterable<RatingCategoryControllerResponse> convertRatingCategoryEntityIterableToResponseDto(Iterable<RatingCategory> ratingCategories){

        var categoryResponses = new ArrayList<RatingCategoryControllerResponse>();

        ratingCategories.forEach(x -> categoryResponses.add(this.convertRatingCategoryEntityToResponseDto(x)));

        return categoryResponses;
    }

    public SubjectControllerResponse convertSubjectEntityToResponseDto(Subject subject){

        return new SubjectControllerResponse(subject.getSubject());

    }

    public Iterable<SubjectControllerResponse> convertSubjectEntityIterableToResponseDto(Iterable<Subject> subjects){

        var subjectResponses = new ArrayList<SubjectControllerResponse>();

        subjects.forEach(x -> subjectResponses.add(this.convertSubjectEntityToResponseDto(x)));

        return subjectResponses;
    }






    private List<FileControllerDto> findNewsFiles(News news){

        List<FileControllerDto> fileControllerDtos = new ArrayList<>();

        news
                .getNewsResources()
                .forEach(x -> {

                    try {
                        fileControllerDtos.add(new FileControllerDto(
                                x.getName(),
                                x.getType(),
                                FileCopyUtils.copyToByteArray(new File(x.getPath()))
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return fileControllerDtos;

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
