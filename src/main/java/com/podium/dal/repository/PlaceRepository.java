package com.podium.dal.repository;

import com.podium.dal.entity.Place;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place,Integer> {

    boolean existsByName(String name);


}
