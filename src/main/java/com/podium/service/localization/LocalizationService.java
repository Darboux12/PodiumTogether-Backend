package com.podium.service.localization;

import com.podium.model.dto.response.localization.LocalizationResponseDto;
import com.podium.model.entity.localization.Localization;
import com.podium.repository.localization.CityRepository;
import com.podium.repository.localization.LocalizationRepository;
import com.podium.repository.localization.StreetRepository;
import org.springframework.stereotype.Service;

@Service
public class LocalizationService {

    private LocalizationRepository localizationRepository;
    private CityRepository cityRepository;
    private StreetRepository streetRepository;

    public LocalizationService(LocalizationRepository localizationRepository, CityRepository cityRepository, StreetRepository streetRepository) {
        this.localizationRepository = localizationRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
    }

    public boolean existLocalization(String city, String street, int num, String postal){

        return this
                .localizationRepository
                .existsByCityAndStreetAndBuildingNumberAndPostalCode(
                        this.cityRepository.findByCity(city),
                        this.streetRepository.findByStreet(street),
                        num,postal
                );

    }

    private LocalizationResponseDto convertEntityToResponseDto(Localization localization){

        return new LocalizationResponseDto(
                localization.getLocalizationId(),
                localization.getCity().getCity(),
                localization.getStreet().getStreet(),
                localization.getBuildingNumber(),
                localization.getPostalCode(),
                localization.getRemarks()
        );

    }

}
