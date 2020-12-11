package com.podium.dal.repository;

import com.podium.dal.entity.WeekDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeekDayRepository extends CrudRepository<WeekDay,Integer> {

   Optional<WeekDay> findByDay(String day);

}
