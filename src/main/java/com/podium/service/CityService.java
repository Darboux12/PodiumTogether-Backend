package com.podium.service;

import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.entity.localization.City;
import com.podium.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean existByCityName(String cityName){
        return this.cityRepository.existsByCity(cityName);
    }

    public CityResponseDto findCityByName(String cityName){
        return this.convertEntityToResponseDto(
                this.cityRepository.findByCity(cityName));
    }

    public Iterable<CityResponseDto> findAllCity(){

        List<CityResponseDto> responseDtos = new ArrayList<>();

        this.cityRepository
                .findAll()
                .forEach(x -> responseDtos
                                .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public void addCity(CityRequestDto requestDto){
        this.cityRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public void deleteCityByName(String name){
        this.cityRepository.deleteByCity(name);
    }

    private City convertRequestDtoToEntity(CityRequestDto requestDto){
        return new City(requestDto.getCity());
    }

    private CityResponseDto convertEntityToResponseDto(City city){

        return new CityResponseDto(city.getCity());

    }

}
