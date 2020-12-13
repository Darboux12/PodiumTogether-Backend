package com.podium.service;

import com.podium.dal.entity.*;
import com.podium.dal.repository.PlaceRepository;
import com.podium.service.dto.BusinessDayServiceDto;
import com.podium.service.dto.LocalizationServiceDto;
import com.podium.service.dto.PlaceAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    private DisciplineService disciplineService;
    private LocalizationService localizationService;
    private BusinessDayService businessDayService;
    private ReviewService reviewService;

    public PlaceService(PlaceRepository placeRepository, DisciplineService disciplineService, LocalizationService localizationService, BusinessDayService businessDayService, ReviewService reviewService) {
        this.placeRepository = placeRepository;
        this.disciplineService = disciplineService;
        this.localizationService = localizationService;
        this.businessDayService = businessDayService;
        this.reviewService = reviewService;
    }

    @Transactional
    public void addPlace(PlaceAddServiceDto addServiceDto){

        if(this.placeRepository.existsByName(addServiceDto.getName()))
            throw new PodiumEntityAlreadyExistException("Place with given name");

        LocalizationServiceDto localizationServiceDto =
                addServiceDto.getLocalizationDto();

        if(this.existLocalization(localizationServiceDto)){

            Localization localization = this.localizationService
                    .findLocalization(localizationServiceDto);

            if(this.placeRepository.existsByLocalization(localization))
                throw new PodiumEntityAlreadyExistException("Place with given localization");

        }

        this.placeRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));

        Place place = this.getEntity(addServiceDto.getName());

        place.getReviews().add(this.reviewService.getEntity(
                place.getName(),
                addServiceDto.getReviewServiceDto()
        ));

    }

    public boolean existByName(String name){
        return this.placeRepository.existsByName(name);
    }

    private Place convertServiceAddDtoToEntity(PlaceAddServiceDto requestDto){

        return new Place(
                requestDto.getName(),
                this.getPlaceDiscipline(requestDto.getDiscipline()),
                this.getPlaceLocalization(requestDto.getLocalizationDto()),
                this.getBusinessDays(requestDto.getBusinessDayDtos()),
                requestDto.getCost(),
                requestDto.getUsageTime(),
                requestDto.getMinAge(),
                requestDto.getMaxAge()
        );
    }

    private Localization getPlaceLocalization(LocalizationServiceDto localizationDto){
        return this.localizationService.getEntity(localizationDto);
    }

    private Discipline getPlaceDiscipline(String disciplineName){
        return this.disciplineService.getEntity(disciplineName);
    }

    private Set<BusinessDay> getBusinessDays(Set<BusinessDayServiceDto> businessDayServiceDtos){

        Set<BusinessDay> businessDays = new HashSet<>();

        businessDayServiceDtos
                .forEach(day -> businessDays.add(this.businessDayService.getEntity(day)));

        return businessDays;

    }

    private boolean existLocalization(LocalizationServiceDto serviceDto){

        return this.localizationService

                .existLocalizationByCityStreetPostalCodeBuildingNumber(
                        serviceDto.getCity(),
                        serviceDto.getStreet(),
                        serviceDto.getPostalCode(),
                        serviceDto.getBuildingNumber()
                );
    }

    public Place getEntity(String placeName){

       return this.placeRepository
               .findByName(placeName)
               .orElseThrow(() -> new PodiumEntityNotFoundException("Place with given name"));
   }

}
