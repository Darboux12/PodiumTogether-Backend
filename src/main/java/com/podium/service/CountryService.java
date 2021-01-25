package com.podium.service;

import com.podium.dal.entity.Country;
import com.podium.dal.entity.User;
import com.podium.dal.repository.CountryRepository;
import com.podium.service.dto.request.CountryAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
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

    public Country getEntity(String countryName) throws PodiumEntityNotFoundException {

        return this.countryRepository
                .findByName(countryName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Country"));

    }

}

