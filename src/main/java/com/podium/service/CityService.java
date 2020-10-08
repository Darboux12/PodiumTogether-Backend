package com.podium.service;

import com.podium.model.entity.City;
import com.podium.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean existByCityName(String cityName){
        return this.cityRepository.existsByName(cityName);
    }

    public City findCityByName(String cityName){
        return this.cityRepository.findByName(cityName);
    }



}
