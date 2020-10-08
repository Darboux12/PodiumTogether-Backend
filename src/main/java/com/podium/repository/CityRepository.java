package com.podium.repository;

import com.podium.model.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City,Integer> {

     boolean existsByName(String cityName);

     City findByName(String cityName);






}
