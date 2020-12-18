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
    public void addLocalization(LocalizationServiceDto localizationServiceDto) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {

        String city = localizationServiceDto.getCity();
        String street = localizationServiceDto.getStreet();
        String postalCode = localizationServiceDto.getPostalCode();

        int buildingNumber = localizationServiceDto.getBuildingNumber();

        if(this.existLocalizationByCityStreetPostalCodeBuildingNumber(city,street,postalCode,buildingNumber))
            throw new PodiumEntityAlreadyExistException("Given localization");

        this.convertServiceAddDtoToEntity(localizationServiceDto);

    }

    @Transactional
    public void deleteLocalizationById(int id) throws PodiumEntityNotFoundException {

        Localization localization= this.localizationRepository.findById(id)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Localization with given id"));

        if(localization.getPlaces().size() == 1)
            this.localizationRepository.delete(localization);

        this.cityService.deleteCity(localization.getCity());

        this.streetService.deleteStreet(localization.getStreet());
    }

    public boolean existLocalizationByCityStreetPostalCodeBuildingNumber(String city, String street, String postalCode, int buildingNumber) throws PodiumEntityNotFoundException {

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

    public Localization findLocalization(LocalizationServiceDto localizationServiceDto) throws PodiumEntityNotFoundException {

        City city = this.cityService.findCityByName(localizationServiceDto.getCity());
        Street street = this.streetService.findStreetByName(localizationServiceDto.getStreet());
        String postalCode = localizationServiceDto.getPostalCode();

        int buildingNumber = localizationServiceDto.getBuildingNumber();

        return this.localizationRepository
                .findByCityAndStreetAndBuildingNumberAndPostalCode(city,street,buildingNumber,postalCode)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Localization"));

    }

    private Localization convertServiceAddDtoToEntity(LocalizationServiceDto addDto) throws PodiumEntityNotFoundException {

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

    public Localization getEntity(LocalizationServiceDto serviceDto){

        City city = this.cityService.getEntity(serviceDto.getCity());

        Street street = this.streetService.getEntity(serviceDto.getStreet());

        String postalCode = serviceDto.getPostalCode();
        String remarks = serviceDto.getLocalizationRemarks();

        int buildingNumber = serviceDto.getBuildingNumber();

        return this.localizationRepository

                .findByCityAndStreetAndBuildingNumberAndPostalCode(city,street,buildingNumber,postalCode)

                .orElse(new Localization(city,street,buildingNumber,postalCode,remarks));

    }

}
