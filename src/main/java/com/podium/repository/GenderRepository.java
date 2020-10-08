package com.podium.repository;

import com.podium.model.entity.Gender;
import org.springframework.data.repository.CrudRepository;

public interface GenderRepository extends CrudRepository<Gender,Integer> {

    boolean existsByName(String genderName);

    Gender findByName(String genderName);


}
