package com.podium.repository;

import com.podium.model.entity.Gender;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface GenderRepository extends CrudRepository<Gender,Integer> {

    boolean existsByName(String genderName);

    Gender findByName(String genderName);

    @Transactional
    void deleteByName(String genderName);


}
