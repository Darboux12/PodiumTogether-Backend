package com.podium.service;

import com.podium.dal.entity.City;
import com.podium.dal.repository.CityRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.dto.CityAddServiceDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional
    public void addCity(CityAddServiceDto cityAddServiceDto){

        if(this.cityRepository.existsByCity(cityAddServiceDto.getCity()))
            throw new PodiumEntityAlreadyExistException("City");

        this.cityRepository.save(this.convertServiceDtoToEntity(cityAddServiceDto));
    }

    @Transactional
    public void deleteCityByName(String cityName){

        if(!this.cityRepository.existsByCity(cityName))
            throw new PodiumEntityNotFoundException("City");

        this.cityRepository.deleteByCity(cityName);
    }

    public boolean existCityByName(String cityName){
        return this.cityRepository.existsByCity(cityName);
    }

    public City findCityByName(String cityName){

        return this.cityRepository
                .findByCity(cityName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("City"));
    }

    public Iterable<City> findAllCity(){
        return this.cityRepository.findAll();
    }

    private City convertServiceDtoToEntity(CityAddServiceDto cityAddServiceDto){
        return new City(cityAddServiceDto.getCity());
    }

    public City getEntity(String cityName){
        return this.cityRepository.findByCity(cityName).orElse(new City(cityName));
    }

}
