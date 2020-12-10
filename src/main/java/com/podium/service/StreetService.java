package com.podium.service;

import com.podium.model.entity.Street;
import com.podium.repository.StreetRepository;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StreetService {

    private StreetRepository streetRepository;

    public StreetService(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }

    public boolean existStreetByName(String streetName){
        return this.streetRepository.existsByStreet(streetName);
    }

    public Street findStreetByName(String streetName){

        return this.streetRepository
                .findByStreet(streetName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("Street"));
    }

    public Iterable<Street> findAllStreet(){
        return this.streetRepository.findAll();
    }

    @Transactional
    public void addStreet(Street street){

        if(this.streetRepository.existsByStreet(street.getStreet()))
            throw new PodiumEntityAlreadyExistException("Street");

        this.streetRepository.save(street);
    }

    @Transactional
    public void deleteStreetByName(String name){

        if(!this.streetRepository.existsByStreet(name))
            throw new PodiumEntityNotFoundException("Street");

        this.streetRepository.deleteByStreet(name);
    }

}