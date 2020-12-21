package com.podium.service;

import com.podium.controller.dto.request.CountryAddControllerRequest;
import com.podium.dal.entity.Country;
import com.podium.dal.repository.CountryRepository;
import com.podium.service.dto.request.CountryAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CountryService {

    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional
    public void addCountry(CountryAddControllerRequest requestDto) throws PodiumEntityAlreadyExistException {

        if(this.countryRepository.existsByName(requestDto.getName()))
            throw new PodiumEntityAlreadyExistException("Country");

        this.countryRepository.save(this.convertRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteCountryByName(String name)
            throws PodiumEntityNotFoundException {

        if(!this.countryRepository.existsByName(name))
            throw new PodiumEntityNotFoundException("Country");

        this.countryRepository.deleteByName(name);
    }

    public boolean existCountryByName(String countryName){
        return this.countryRepository.existsByName(countryName);
    }

    public Iterable<Country> findAllCountry(){
        return this.countryRepository.findAll();

    }

    public Country findCountryByName(String countryName) throws PodiumEntityNotFoundException {

        return this.countryRepository
                .findByName(countryName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Country"));

    }

    private Country convertRequestDtoToEntity(CountryAddControllerRequest requestDto){

        return new Country(
                requestDto.getCountryId(),
                requestDto.getName(),
                requestDto.getPrintableName(),
                requestDto.getIso3(),
                requestDto.getNumCode()
        );
    }

    private Country convertServiceAddDtoToEntity(CountryAddServiceDto addServiceDto){

        return new Country(
                addServiceDto.getCountryId(),
                addServiceDto.getName(),
                addServiceDto.getPrintableName(),
                addServiceDto.getIso3(),
                addServiceDto.getNumCode()
        );
    }

    public Country getEntity(String countryName) throws PodiumEntityNotFoundException {

        return this.countryRepository
                .findByName(countryName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Country"));

    }


}

