package com.podium.dal.repository;

import com.podium.dal.entity.City;
import com.podium.dal.entity.Localization;
import com.podium.dal.entity.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization,Integer> {

    boolean existsByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

    Optional<Localization> findByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

}
