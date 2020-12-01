package com.podium.repository.localization;

import com.podium.model.entity.localization.Country;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface CountryRepository extends CrudRepository<Country,Integer> {

    Country findByName(String countryName);

    boolean existsByName(String countryName);

    @Transactional
    void deleteByName(String discipline);

}
