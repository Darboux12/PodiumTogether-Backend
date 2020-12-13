package com.podium.dal.repository;

import com.podium.dal.entity.Localization;
import com.podium.dal.entity.Place;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlaceRepository extends CrudRepository<Place,Integer> {

    boolean existsByName(String name);

    Optional<Place> findByName(String placeName);

    boolean existsByLocalization(Localization localization);


}
