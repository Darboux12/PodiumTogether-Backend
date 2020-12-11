package com.podium.service;

import com.podium.dal.entity.City;
import com.podium.dal.entity.Localization;
import com.podium.dal.entity.Street;
import com.podium.dal.repository.LocalizationRepository;
import com.podium.service.dto.LocalizationServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Transactional
    public void addLocalization(LocalizationServiceDto localizationServiceDto){

        String city = localizationServiceDto.getCity();
        String street = localizationServiceDto.getStreet();
        String postalCode = localizationServiceDto.getPostalCode();

        int buildingNumber = localizationServiceDto.getBuildingNumber();

        if(this.existLocalizationByCityStreetPostalCodeBuildingNumber(city,street,postalCode,buildingNumber))
            throw new PodiumEntityAlreadyExistException("Given localization");

        this.convertServiceAddDtoToEntity(localizationServiceDto);

    }

    public boolean existLocalizationByCityStreetPostalCodeBuildingNumber(String city, String street, String postalCode, int buildingNumber){

       if(this.cityService.existCityByName(city) &&
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

    public Localization findLocalization(LocalizationServiceDto localizationServiceDto){

        City city = this.cityService.findCityByName(localizationServiceDto.getCity());
        Street street = this.streetService.findStreetByName(localizationServiceDto.getStreet());
        String postalCode = localizationServiceDto.getPostalCode();

        int buildingNumber = localizationServiceDto.getBuildingNumber();

        return this.localizationRepository
                .findByCityAndStreetAndBuildingNumberAndPostalCode(city,street,buildingNumber,postalCode)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Localization"));

    }

    private Localization convertServiceAddDtoToEntity(LocalizationServiceDto addDto){

        City city = this.cityService.findCityByName(addDto.getCity());

        Street street = this.streetService.findStreetByName(addDto.getStreet());

       return this.localizationRepository.save(
                new Localization(
                        city,street,
                        addDto.getBuildingNumber(),
                        addDto.getPostalCode(),
                        addDto.getLocalizationRemarks())
        );
    }

}
