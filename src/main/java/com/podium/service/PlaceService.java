package com.podium.service;

import com.podium.repository.PlaceRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public boolean existByName(String name){
        return this.placeRepository.existsByName(name);
    }


}
