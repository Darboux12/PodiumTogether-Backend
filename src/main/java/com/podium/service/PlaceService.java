package com.podium.service;

import com.podium.model.dto.other.OpeningDay;
import com.podium.model.dto.request.place.PlaceRequestDto;
import com.podium.model.entity.Discipline;
import com.podium.model.entity.City;
import com.podium.model.entity.Localization;
import com.podium.model.entity.Street;
import com.podium.model.entity.Place;
import com.podium.model.entity.BusinessDay;
import com.podium.repository.DisciplineRepository;
import com.podium.repository.CityRepository;
import com.podium.repository.LocalizationRepository;
import com.podium.repository.StreetRepository;
import com.podium.repository.PlaceRepository;
import com.podium.repository.BusinessDayRepository;
import com.podium.repository.WeekDayRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;
    private DisciplineRepository disciplineRepository;
    private LocalizationRepository localizationRepository;
    private CityRepository cityRepository;
    private StreetRepository streetRepository;
    private BusinessDayRepository businessDayRepository;
    private WeekDayRepository weekDayRepository;

    public PlaceService(PlaceRepository placeRepository, DisciplineRepository disciplineRepository, LocalizationRepository localizationRepository, CityRepository cityRepository, StreetRepository streetRepository, BusinessDayRepository businessDayRepository, WeekDayRepository weekDayRepository) {
        this.placeRepository = placeRepository;
        this.disciplineRepository = disciplineRepository;
        this.localizationRepository = localizationRepository;
        this.cityRepository = cityRepository;
        this.streetRepository = streetRepository;
        this.businessDayRepository = businessDayRepository;
        this.weekDayRepository = weekDayRepository;
    }

    public boolean existByName(String name){
        return this.placeRepository.existsByName(name);
    }



    private Place convertRequestDtoToEntity(PlaceRequestDto requestDto){

        /*

        Discipline discipline =
                disciplineRepository.findByDiscipline(requestDto.getName());

        String requestCity = requestDto.getPlaceLocalization().getCity();

        City city = this.cityRepository.existsByCity(requestCity)

                ? this.cityRepository.findByCity(requestCity)

                : new City(requestCity);




        String requestStreet = requestDto.getPlaceLocalization().getStreet();

        Street street = this.streetRepository.existsByStreet(requestStreet)

                ? this.streetRepository.findByStreet(requestStreet)

                : new Street(requestStreet);







         */



        Set<BusinessDay> businessDays = new HashSet<>();


        for(OpeningDay openingDay : requestDto.getOpeningDays()){


            BusinessDay businessDay =
                    this.businessDayRepository
                            .existsByDayAndOpenAndOpenTimeFromAndOpenTimeTo(
                                    this.weekDayRepository.findByDay(openingDay.getDay()),
                                    openingDay.isOpen(),
                                    openingDay.getOpeningTimeFrom(),
                                    openingDay.getOpeningTimeTo()

                            ) ?

                            this.businessDayRepository
                                    .findByDayAndOpenIsAndOpenTimeFromAndOpenTimeTo(
                                            this.weekDayRepository.findByDay(openingDay.getDay()),
                                            openingDay.isOpen(),
                                            openingDay.getOpeningTimeFrom(),
                                            openingDay.getOpeningTimeTo()
                                    )
                            :

                            new BusinessDay(
                                    this.weekDayRepository.findByDay(openingDay.getDay()),
                                    openingDay.isOpen(),
                                    openingDay.getOpeningTimeFrom(),
                                    openingDay.getOpeningTimeTo()
                            );

            businessDays.add(businessDay);

        }























    return null;


    }


    private Discipline findDisciplineEnitiy(String discipline){
        return disciplineRepository.findByDiscipline(discipline);
    }

    private City findCityEntity(String city){

        return this.cityRepository.existsByCity(city)
                ? this.cityRepository.findByCity(city)
                : new City(city);

    }

    private Street findStreetEntity(String street){

        return this.streetRepository.existsByStreet(street)
                ? this.streetRepository.findByStreet(street)
                : new Street(street);
    }

    private Localization findLocalizationEntity
            (City city, Street street, int buildingNumber, String postalCode,String remarks){

       return this.localizationRepository

               .findByCityAndStreetAndBuildingNumberAndPostalCode
                       (city,street,buildingNumber,postalCode)

               .orElse(new Localization(city,street,buildingNumber,postalCode,remarks));

    }



}
