package com.podium.repository;

import com.podium.model.entity.City;
import com.podium.model.entity.Gender;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface CityRepository extends CrudRepository<City,Integer> {

     boolean existsByName(String cityName);

     City findByName(String cityName);

     @Transactional
     void deleteByName(String cityName);

}
