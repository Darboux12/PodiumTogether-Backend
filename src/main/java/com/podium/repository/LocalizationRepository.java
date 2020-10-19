package com.podium.repository;

import com.podium.model.entity.City;
import com.podium.model.entity.Localization;
import com.podium.model.entity.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization,Integer> {

    boolean existsByCityAndStreetAndNumberAndPostalCode(City City, Street Street, int number, String postalCode);

    Localization findByCityAndStreetAndNumberAndPostalCode(City City, Street Street, int number, String postalCode);


}
