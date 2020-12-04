package com.podium.service;

import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.entity.City;
import com.podium.repository.CityRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean existByCityName(String cityName){
        return this.cityRepository.existsByCity(cityName);
    }

    public CityResponseDto findCityByName(String cityName){

        City city = this.cityRepository.findByCity(cityName).orElseThrow(() ->

                new PodiumEntityNotFoundException("City"));

        return this.convertEntityToResponseDto(city);
    }

    public Iterable<CityResponseDto> findAllCity(){

        var responseDtos = new ArrayList<CityResponseDto>();

        this.cityRepository
                .findAll()
                .forEach(x -> responseDtos
                                .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    @Transactional
    public void addCity(CityRequestDto requestDto){

        if(this.cityRepository.existsByCity(requestDto.getCity()))
            throw new PodiumEntityAlreadyExistException("City");

        this.cityRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteCityByName(String name){

        if(!this.cityRepository.existsByCity(name))
            throw new PodiumEntityNotFoundException("City");

        this.cityRepository.deleteByCity(name);
    }

    private City convertRequestDtoToEntity(CityRequestDto requestDto){
        return new City(requestDto.getCity());
    }

    private CityResponseDto convertEntityToResponseDto(City city){
        return new CityResponseDto(city.getCity());
    }

}
