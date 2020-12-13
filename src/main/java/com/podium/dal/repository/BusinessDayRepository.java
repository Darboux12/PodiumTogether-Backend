package com.podium.dal.repository;

import com.podium.dal.entity.WeekDay;
import com.podium.dal.entity.BusinessDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface BusinessDayRepository extends CrudRepository<BusinessDay,Integer> {

    boolean existsByDayAndOpenAndOpenTimeFromAndOpenTimeTo
            (WeekDay weekDay, boolean isOpen, LocalTime openTimeFrom, LocalTime openTimeTo);

   Optional<BusinessDay>findByDayAndOpenIsAndOpenTimeFromAndOpenTimeTo
            (WeekDay weekDay,boolean isOpen,LocalTime openTimeFrom,LocalTime openTimeTo);
}
