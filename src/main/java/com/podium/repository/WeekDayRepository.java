package com.podium.repository;

import com.podium.model.entity.time.WeekDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDayRepository extends CrudRepository<WeekDay,Integer> {

}
