package com.podium.repository.localization;

import com.podium.model.entity.localization.City;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface CityRepository extends CrudRepository<City,Integer> {

     boolean existsByCity(String cityName);

     City findByCity(String cityName);

     @Transactional
     void deleteByCity(String cityName);

}
