package com.podium.service;

import com.podium.model.dto.other.BusinessDayDto;
import com.podium.model.dto.request.PlaceRequestDto;
import com.podium.model.entity.*;
import com.podium.repository.DisciplineRepository;
import com.podium.repository.PlaceRepository;
import com.podium.repository.BusinessDayRepository;
import com.podium.repository.WeekDayRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    private DisciplineService disciplineService;
    private LocalizationService localizationService;

    private BusinessDayRepository businessDayRepository;
    private WeekDayRepository weekDayRepository;

    public PlaceService(PlaceRepository placeRepository, DisciplineRepository disciplineRepository, DisciplineService disciplineService,BusinessDayRepository businessDayRepository, WeekDayRepository weekDayRepository) {
        this.placeRepository = placeRepository;
        this.disciplineService = disciplineService;
        this.businessDayRepository = businessDayRepository;
        this.weekDayRepository = weekDayRepository;
    }

    public boolean existByName(String name){
        return this.placeRepository.existsByName(name);
    }

    private Place convertRequestDtoToEntity(PlaceRequestDto requestDto){

        String name = requestDto.getName();

        Discipline discipline = this.disciplineService.findByDisciplineName(requestDto.getDiscipline());

        Localization localization = this.localizationService.findLocalization(requestDto.getLocalizationDto());





        Set<BusinessDay> businessDays = new HashSet<>();


        for(BusinessDayDto businessDayDto : requestDto.getBusinessDayDtos()){


            BusinessDay businessDay =
                    this.businessDayRepository
                            .existsByDayAndOpenAndOpenTimeFromAndOpenTimeTo(
                                    this.weekDayRepository.findByDay(businessDayDto.getDay()),
                                    businessDayDto.isOpen(),
                                    businessDayDto.getOpeningTimeFrom(),
                                    businessDayDto.getOpeningTimeTo()

                            ) ?

                            this.businessDayRepository
                                    .findByDayAndOpenIsAndOpenTimeFromAndOpenTimeTo(
                                            this.weekDayRepository.findByDay(businessDayDto.getDay()),
                                            businessDayDto.isOpen(),
                                            businessDayDto.getOpeningTimeFrom(),
                                            businessDayDto.getOpeningTimeTo()
                                    )
                            :

                            new BusinessDay(
                                    this.weekDayRepository.findByDay(businessDayDto.getDay()),
                                    businessDayDto.isOpen(),
                                    businessDayDto.getOpeningTimeFrom(),
                                    businessDayDto.getOpeningTimeTo()
                            );

            businessDays.add(businessDay);

        }























    return null;


    }

}
