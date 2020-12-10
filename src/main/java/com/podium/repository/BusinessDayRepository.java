package com.podium.repository;

import com.podium.model.entity.BusinessDay;
import com.podium.model.entity.WeekDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface BusinessDayRepository extends CrudRepository<BusinessDay,Integer> {

    boolean existsByDayAndOpenAndOpenTimeFromAndOpenTimeTo
            (WeekDay weekDay,boolean isOpen,LocalTime openTimeFrom,LocalTime openTimeTo);

    BusinessDay findByDayAndOpenIsAndOpenTimeFromAndOpenTimeTo
            (WeekDay weekDay,boolean isOpen,LocalTime openTimeFrom,LocalTime openTimeTo);
}
