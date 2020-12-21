package com.podium.service;

import com.podium.dal.entity.*;
import com.podium.dal.repository.PlaceRepository;
import com.podium.service.dto.other.BusinessDayServiceDto;
import com.podium.service.dto.other.LocalizationServiceDto;
import com.podium.service.dto.request.PlaceAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
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
    private ResourceService resourceService;

    public PlaceService(PlaceRepository placeRepository, DisciplineService disciplineService, LocalizationService localizationService, BusinessDayService businessDayService, ResourceService resourceService) {
        this.placeRepository = placeRepository;
        this.disciplineService = disciplineService;
        this.localizationService = localizationService;
        this.businessDayService = businessDayService;
        this.resourceService = resourceService;
    }

    @Transactional
    public void addPlace(PlaceAddServiceDto addServiceDto) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError {

        if(this.placeRepository.existsByName(addServiceDto.getName()))
            throw new PodiumEntityAlreadyExistException("Place with given name");

        LocalizationServiceDto localizationServiceDto =
                addServiceDto.getLocalizationDto();

        if(this.existLocalization(localizationServiceDto)){

            Localization localization = this.localizationService
                    .findLocalization(localizationServiceDto);

            if(this.placeRepository.existsByPlaceLocalization(localization))
                throw new PodiumEntityAlreadyExistException("Place with given localization");

        }

        this.placeRepository.save(this.convertServiceAddDtoToEntity(addServiceDto));

    }

    @Transactional
    public void deletePlaceById(int id) throws PodiumEntityNotFoundException {

        Place place = this.placeRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Place with given id"));

        this.placeRepository.deleteById(id);

        this.resourceService.deleteResources(place.getPlaceImages());

        this.localizationService
                .deleteLocalizationById(place.getPlaceLocalization().getId());

        this.businessDayService.deleteBusinessDays(place.getBusinessDays());


    }

    public Iterable<Place> findAllPlace(){
        return this.placeRepository.findAll();
    }

    public boolean existByName(String name){
        return this.placeRepository.existsByName(name);
    }

    public Place findPlaceByName(String placeName) throws PodiumEntityNotFoundException {

        return this.placeRepository
                .findByName(placeName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Place with given name"));

    }

    private Place convertServiceAddDtoToEntity(PlaceAddServiceDto requestDto) throws PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError {

        Discipline discipline = this.getPlaceDiscipline(requestDto.getDiscipline());

        Localization localization = this.getPlaceLocalization(requestDto.getLocalizationDto());

        Set<BusinessDay> businessDays = this.getBusinessDays(requestDto.getBusinessDayDtos());

        Set<PodiumResource> imageResources = this.resourceService
                .createPodiumImageResources(requestDto.getImages());

        Set<PodiumResource> documentResources = this.resourceService
                .createPodiumDocumentResources(requestDto.getDocuments());

        Set<PodiumResource> resources = new HashSet<>();

        resources.addAll(imageResources);
        resources.addAll(documentResources);

        return new Place(
                requestDto.getName(),
                discipline,
                localization,
                businessDays,
                requestDto.getCost(),
                requestDto.getUsageTime(),
                requestDto.getMinAge(),
                requestDto.getMaxAge(),
                resources
        );
    }

    private Localization getPlaceLocalization(LocalizationServiceDto localizationDto){
        return this.localizationService.getEntity(localizationDto);
    }

    private Discipline getPlaceDiscipline(String disciplineName) throws PodiumEntityNotFoundException {
        return this.disciplineService.getEntity(disciplineName);
    }

    private Set<BusinessDay> getBusinessDays(Set<BusinessDayServiceDto> businessDayServiceDtos) throws PodiumEntityTimeConsistencyError, PodiumEntityNotFoundException {

        Set<BusinessDay> businessDays = new HashSet<>();

        for(BusinessDayServiceDto dto : businessDayServiceDtos)
            businessDays.add(this.businessDayService.getEntity(dto));

        return businessDays;

    }

    private boolean existLocalization(LocalizationServiceDto serviceDto) throws PodiumEntityNotFoundException {

        return this.localizationService

                .existLocalizationByCityStreetPostalCodeBuildingNumber(
                        serviceDto.getCity(),
                        serviceDto.getStreet(),
                        serviceDto.getPostalCode(),
                        serviceDto.getBuildingNumber()
                );
    }

    public Place getEntity(String placeName) throws PodiumEntityNotFoundException {

       return this.placeRepository
               .findByName(placeName)
               .orElseThrow(() -> new PodiumEntityNotFoundException("Place with given name"));
   }

}
