package com.podium.repository;

import com.podium.model.entity.Country;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country,Integer> {

    Optional<Country> findByName(String countryName);

    boolean existsByName(String countryName);

    @Transactional
    void deleteByName(String discipline);

}
