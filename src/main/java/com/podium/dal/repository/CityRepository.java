package com.podium.dal.repository;

import com.podium.dal.entity.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City,Integer> {

     boolean existsByCity(String cityName);

     Optional<City> findByCity(String cityName);

     void deleteByCity(String cityName);

}
