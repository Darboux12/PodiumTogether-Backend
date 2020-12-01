package com.podium.repository.time;

import com.podium.model.entity.time.WeekDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekDayRepository extends CrudRepository<WeekDay,Integer> {

    WeekDay findByDay(String day);

}
