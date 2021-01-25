package com.podium.controller.dto.converter;

import com.podium.controller.dto.request.*;
import com.podium.service.dto.other.BusinessDayServiceDto;
import com.podium.service.dto.other.LocalizationServiceDto;
import com.podium.service.dto.other.StarRatingServiceDto;
import com.podium.service.dto.request.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

public class ControllerRequestConverter {

    private static ControllerRequestConverter instance;

    private ControllerRequestConverter() {}

    public static ControllerRequestConverter getInstance() {
        if(instance == null) {
            instance = new ControllerRequestConverter();
        }
        return instance;
    }

    public UserRoleUpdateServiceRequest convertGrantRoleRequestToServiceRequest(UserRoleUpdateControllerRequest request, String adminUsername){
        return new UserRoleUpdateServiceRequest(
                adminUsername,
                request.getUsername(),
                request.getRole()
        );
    }

    public CityAddServiceRequest convertCityAddRequestToServiceDto(CityAddControllerRequest request){
        return new CityAddServiceRequest(request.getCity());
    }

    public CityDeleteServiceRequest convertCityDeleteRequestToServiceDto(String cityName){
        return new CityDeleteServiceRequest(cityName);
    }

    public ContactAddServiceRequest convertContactAddRequestToServiceDto(ContactAddControllerRequest request ){

        return new ContactAddServiceRequest(
                request.getUserEmail(),
                request.getSubject(),
                request.getMessage()
        );
    }

    public CountryAddServiceRequest convertCountryAddRequestToServiceDto(CountryAddControllerRequest request ){

        return new CountryAddServiceRequest(request.getCountryId(),
                request.getName(),
                request.getPrintableName(),
                request.getIso3(),
                request.getNumCode()
        );
    }

    public DisciplineAddServiceRequest convertDisciplineAddRequestToServiceModel(DisciplineAddControllerRequest requestDto){
        return new DisciplineAddServiceRequest(requestDto.getDiscipline());
    }

    public GenderAddServiceRequest convertGenderAddRequestToServiceDto(GenderAddControllerRequest request){
        return new GenderAddServiceRequest(request.getGender());
    }

    public NewsAddServiceRequest convertNewsAddRequestToServiceDto(NewsAddControllerRequest request, List<MultipartFile> images){
        return new NewsAddServiceRequest(
                request.getTitle(),
                request.getShortText(),
                request.getLinkText(),
                request.getText(),
                new HashSet<>(images)
        );
    }

    public PlaceAddServiceRequest convertPlaceAddRequestToServiceDto(PlaceAddControllerRequest addRequest, List<MultipartFile> images, List<MultipartFile> documents){

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

    public RatingCategoryAddServiceRequest convertRatingCategoryAddRequestToServiceDto(RatingCategoryAddControllerRequest request){
        return new RatingCategoryAddServiceRequest(request.getCategory());
    }

    public ReviewAddServiceRequest convertReviewAddRequestToServiceDto(ReviewAddControllerRequest addRequest, List<MultipartFile> images){

        var ratingDtos = new HashSet<StarRatingServiceDto>();

        addRequest.getStarRatings().forEach(rating ->
                ratingDtos.add(new StarRatingServiceDto(rating.getCategory(), rating.getRating()
                )));


        return new ReviewAddServiceRequest(
                addRequest.getAuthor(),
                addRequest.getPlace(),
                ratingDtos,
                addRequest.getOpinion(),
                new HashSet<>(images)
        );


    }

    public SubjectAddServiceRequest convertSubjectAddRequestToServiceDto(SubjectAddControllerRequest request){
        return new SubjectAddServiceRequest(request.getSubject());
    }
}
