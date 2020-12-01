package com.podium.repository.localization;

import com.podium.model.entity.localization.City;
import com.podium.model.entity.localization.Localization;
import com.podium.model.entity.localization.Street;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizationRepository extends CrudRepository<Localization,Integer> {

    boolean existsByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

    Optional<Localization> findByCityAndStreetAndBuildingNumberAndPostalCode(City City, Street Street, int number, String postalCode);

}
