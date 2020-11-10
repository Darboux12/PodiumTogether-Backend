package com.podium.repository;

import com.podium.model.entity.localization.City;
import com.podium.model.entity.localization.Localization;
import com.podium.model.entity.localization.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization,Integer> {

    boolean existsByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

    Localization findByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);


}
