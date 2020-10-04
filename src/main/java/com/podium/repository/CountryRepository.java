package com.podium.repository;

import com.podium.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country,Integer> {

    Country findByName(String countryName);


}
