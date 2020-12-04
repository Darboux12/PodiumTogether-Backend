package com.podium.repository;

import com.podium.model.entity.City;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CityRepository extends CrudRepository<City,Integer> {

     boolean existsByCity(String cityName);

     Optional<City> findByCity(String cityName);

     void deleteByCity(String cityName);

}
