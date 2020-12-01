package com.podium.repository.time;

import com.podium.model.entity.time.BusinessDay;
import com.podium.model.entity.time.WeekDay;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalTime;

public interface BusinessDayRepository extends CrudRepository<BusinessDay,Integer> {

    boolean existsByDayAndOpenAndOpenTimeFromAndOpenTimeTo
            (WeekDay weekDay,boolean isOpen,LocalTime openTimeFrom,LocalTime openTimeTo);

    BusinessDay findByDayAndOpenIsAndOpenTimeFromAndOpenTimeTo
            (WeekDay weekDay,boolean isOpen,LocalTime openTimeFrom,LocalTime openTimeTo);
}
