package com.podium.service;

import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.model.entity.City;
import com.podium.model.entity.Gender;
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
        return this.cityRepository.existsByName(cityName);
    }

    public CityResponseDto findCityByName(String cityName){
        return this.convertEntityToResponseDto(
                this.cityRepository.findByName(cityName));
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
        this.cityRepository.deleteByName(name);
    }

    private City convertRequestDtoToEntity(CityRequestDto requestDto){

        City city = new City();
        city.setName(requestDto.getCity());
        return city;

    }

    private CityResponseDto convertEntityToResponseDto(City city){

        CityResponseDto responseDto = new CityResponseDto();
        responseDto.setCity(city.getName());
        responseDto.setId(city.getCityId());
        return responseDto;

    }

}
