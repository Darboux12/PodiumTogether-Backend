package com.podium.repository;

import com.podium.model.entity.WeekDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDayRepository extends CrudRepository<WeekDay,Integer> {

    WeekDay findByDay(String day);

}
