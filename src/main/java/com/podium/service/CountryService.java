package com.podium.service;

import com.podium.model.dto.response.CountryResponseDto;
import com.podium.model.entity.Country;
import com.podium.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    private CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public boolean existCountryByName(String countryName){
        return this.countryRepository.existsByName(countryName);
    }

    public void addCountry(CountryRequestDto requestDto){
        this.countryRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    public void deleteCountryByName(String name){
        this.countryRepository.deleteByName(name);
    }

    public Iterable<CountryResponseDto> findAllCountry(){

        List<CountryResponseDto> responseDtos = new ArrayList<>();

        for(Country country : this.countryRepository.findAll())
            responseDtos.add(this.convertEntityToResponseDto(country));

        return responseDtos;

    }

    private Country convertRequestDtoToEntity(CountryRequestDto requestDto){

        Country country = new Country();
        country.setCountryId(requestDto.getCountryId());
        country.setIso3(requestDto.getIso3());
        country.setName(requestDto.getName());
        country.setPrintable_name(requestDto.getPrintableName());
        country.setNumCode(requestDto.getNumCode());

        return country;

    }

    private CountryResponseDto convertEntityToResponseDto(Country country){

        CountryResponseDto responseDto = new CountryResponseDto();
        responseDto.setCountryId(country.getCountryId());
        responseDto.setIso3(country.getIso3());
        responseDto.setName(country.getName());
        responseDto.setPrintable_name(country.getPrintable_name());
        responseDto.setNumCode(country.getNumCode());

        return responseDto;

    }

}

