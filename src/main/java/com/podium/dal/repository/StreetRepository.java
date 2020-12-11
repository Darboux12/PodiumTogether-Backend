package com.podium.dal.repository;

import com.podium.dal.entity.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreetRepository extends CrudRepository<Street,Integer> {

    boolean existsByStreet(String streetName);

    Optional<Street> findByStreet(String streetName);

    void deleteByStreet(String streetName);


}
