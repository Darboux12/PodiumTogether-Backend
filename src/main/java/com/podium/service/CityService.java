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

        for(City city : this.cityRepository.findAll())
            responseDtos.add(this.convertEntityToResponseDto(city));

        return responseDtos;
    }

    public void addCity(CityRequestDto requestDto){
        this.cityRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public void deleteCityByName(String name){
        this.cityRepository.deleteByCity(name);
    }

    private City convertRequestDtoToEntity(CityRequestDto requestDto){

        City city = new City();
        city.setCity(requestDto.getCity());
        return city;

    }

    private CityResponseDto convertEntityToResponseDto(City city){

        CityResponseDto responseDto = new CityResponseDto();
        responseDto.setCity(city.getCity());
        return responseDto;

    }

}
