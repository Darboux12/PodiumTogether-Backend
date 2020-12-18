package com.podium.service;

import com.podium.dal.entity.BusinessDay;
import com.podium.dal.entity.WeekDay;
import com.podium.dal.repository.BusinessDayRepository;
import com.podium.service.dto.BusinessDayServiceDto;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.exception.PodiumEntityTimeConsistencyError;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class BusinessDayService {

    private BusinessDayRepository businessDayRepository;

    private WeekDayService weekDayService;

    public BusinessDayService(BusinessDayRepository businessDayRepository, WeekDayService weekDayService) {
        this.businessDayRepository = businessDayRepository;
        this.weekDayService = weekDayService;
    }

    public void addBusinessDay(BusinessDayServiceDto serviceDto) throws PodiumEntityNotFoundException {

        this.businessDayRepository.save(
                this.convertServiceDtoToEntity(serviceDto)
        );

    }

    public void deleteBusinessDays(Set<BusinessDay> businessDays){

        var daysToDelete = new HashSet<BusinessDay>();

        businessDays.forEach(day -> {

            if(day.getPlaces().size() == 1)
                daysToDelete.add(day);

        });

        this.businessDayRepository.deleteAll(daysToDelete);


    }

    private BusinessDay convertServiceDtoToEntity(BusinessDayServiceDto businessDayServiceDto) throws PodiumEntityNotFoundException {

        WeekDay weekDay = this.weekDayService.getEntity(businessDayServiceDto.getDay());

        return new BusinessDay(
                weekDay,
                businessDayServiceDto.isOpen(),
                businessDayServiceDto.getOpeningTimeFrom(),
                businessDayServiceDto.getOpeningTimeTo()
        );
    }

    public BusinessDay getEntity(BusinessDayServiceDto businessDayServiceDto) throws PodiumEntityNotFoundException, PodiumEntityTimeConsistencyError {

        WeekDay weekDay = this.weekDayService.getEntity(businessDayServiceDto.getDay());

        LocalTime timeFrom = businessDayServiceDto.getOpeningTimeFrom();

        LocalTime timeTo = businessDayServiceDto.getOpeningTimeTo();

        if(timeFrom.isAfter(timeTo))
            throw new PodiumEntityTimeConsistencyError("Business Day Opening Times");

        return this.businessDayRepository

                .findByDayAndOpenIsAndOpenTimeFromAndOpenTimeTo(
                        weekDay,
                        businessDayServiceDto.isOpen(),
                        businessDayServiceDto.getOpeningTimeFrom(),
                        businessDayServiceDto.getOpeningTimeTo()
                ).orElse(new BusinessDay(
                        weekDay,
                        businessDayServiceDto.isOpen(),
                        businessDayServiceDto.getOpeningTimeFrom(),
                        businessDayServiceDto.getOpeningTimeTo()
                ));

    }



}
