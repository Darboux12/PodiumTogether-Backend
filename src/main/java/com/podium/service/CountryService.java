package com.podium.service;

import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.model.entity.Country;
import com.podium.repository.CountryRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {

    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public boolean existCountryByName(String countryName){
        return this.countryRepository.existsByName(countryName);
    }

    @Transactional
    public void addCountry(CountryRequestDto requestDto){

        if(this.countryRepository.existsByName(requestDto.getName()))
            throw new PodiumEntityAlreadyExistException("Country");

        this.countryRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteCountryByName(String name){

        if(!this.countryRepository.existsByName(name))
            throw new PodiumEntityNotFoundException("Country");

        this.countryRepository.deleteByName(name);
    }

    public Iterable<CountryResponseDto> findAllCountry(){

        List<CountryResponseDto> responseDtos = new ArrayList<>();

        this.countryRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;

    }

    private Country convertRequestDtoToEntity(CountryRequestDto requestDto){

        return new Country(
                requestDto.getCountryId(),
                requestDto.getName(),
                requestDto.getPrintableName(),
                requestDto.getIso3(),
                requestDto.getNumCode()
        );
    }

    private CountryResponseDto convertEntityToResponseDto(Country country){

        return new CountryResponseDto(
                country.getCountryId(),
                country.getName(),
                country.getPrintableName(),
                country.getIso3(),
                country.getNumCode()
        );

    }

}

