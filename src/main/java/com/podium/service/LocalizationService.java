package com.podium.service;

import com.podium.model.dto.other.LocalizationDto;
import com.podium.model.dto.response.LocalizationResponseDto;
import com.podium.model.entity.City;
import com.podium.model.entity.Localization;
import com.podium.model.entity.Street;
import com.podium.repository.CityRepository;
import com.podium.repository.LocalizationRepository;
import com.podium.repository.StreetRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LocalizationService {

    private LocalizationRepository localizationRepository;

    private CityService cityService;
    private StreetService streetService;

    public LocalizationService(LocalizationRepository localizationRepository, CityService cityService, StreetService streetService) {
        this.localizationRepository = localizationRepository;
        this.cityService = cityService;
        this.streetService = streetService;
    }

    public void addLocalization(LocalizationDto dto){

        if(this.existLocalization(dto))
            throw new PodiumEntityAlreadyExistException("Given localization");

        City city = this.cityService.findCityByName(dto.getCity());

        Street street = this.streetService.findStreetByName(dto.getStreet());

       this.localizationRepository.save(
               new Localization(
                       city,street,
                       dto.getBuildingNumber(),
                       dto.getPostalCode(),
                       dto.getLocalizationRemarks())
       );

    }

    public boolean existLocalization(LocalizationDto localizationDto){

       String city = localizationDto.getCity();
       String street = localizationDto.getStreet();
       String postalCode = localizationDto.getPostalCode();

       int buildingNumber = localizationDto.getBuildingNumber();

       if(this.cityService.existByCityName(city) &&
               this.streetService.existStreetByName(street))

       return
               this.localizationRepository
                       .existsByCityAndStreetAndBuildingNumberAndPostalCode(
                               this.cityService.findCityByName(city),
                               this.streetService.findStreetByName(street),
                               buildingNumber,postalCode
                       );

       else return false;
    }

    public Localization findLocalization(LocalizationDto localizationDto){

        City city = this.cityService.findCityByName(localizationDto.getCity());
        Street street = this.streetService.findStreetByName(localizationDto.getStreet());
        String postalCode = localizationDto.getPostalCode();

        int buildingNumber = localizationDto.getBuildingNumber();

        return this.localizationRepository
                .findByCityAndStreetAndBuildingNumberAndPostalCode(city,street,buildingNumber,postalCode)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Localization"));

    }
}
