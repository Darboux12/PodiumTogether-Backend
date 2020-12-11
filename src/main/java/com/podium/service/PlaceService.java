package com.podium.service;

import com.podium.dal.entity.Place;
import com.podium.dal.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    /*

    private PlaceRepository placeRepository;

    private DisciplineService disciplineService;
    private LocalizationService localizationService;

    public PlaceService(PlaceRepository placeRepository, DisciplineService disciplineService, LocalizationService localizationService) {
        this.placeRepository = placeRepository;
        this.disciplineService = disciplineService;
        this.localizationService = localizationService;
    }


    public boolean existByName(String name){
        return this.placeRepository.existsByName(name);
    }

    private Place convertRequestDtoToEntity(PlaceAddRequestDto requestDto){

        String name = requestDto.getName();

        Discipline discipline = this.disciplineService.convertToEntityByDiscipline(requestDto.getDiscipline());

        Localization localization = this.localizationService.





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
 */
}
