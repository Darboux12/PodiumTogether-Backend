package com.podium.repository;

import com.podium.model.entity.City;
import com.podium.model.entity.Localization;
import com.podium.model.entity.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization,Integer> {

    boolean existsByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

    Optional<Localization> findByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

}
