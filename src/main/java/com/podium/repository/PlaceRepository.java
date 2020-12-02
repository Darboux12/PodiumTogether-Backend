package com.podium.repository;

import com.podium.model.entity.Place;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place,Integer> {

    boolean existsByName(String name);


}
