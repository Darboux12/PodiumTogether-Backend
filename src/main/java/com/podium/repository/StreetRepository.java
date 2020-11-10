package com.podium.repository;

import com.podium.model.entity.localization.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends CrudRepository<Street,Integer> {

    boolean existsByStreet(String streetName);

    Street findByStreet(String streetName);
}
