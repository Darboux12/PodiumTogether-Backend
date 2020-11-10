package com.podium.repository;

import com.podium.model.entity.gender.Gender;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface GenderRepository extends CrudRepository<Gender,Integer> {

    boolean existsByGender(String genderName);

    Gender findByGender(String genderName);

    @Transactional
    void deleteByGender(String genderName);


}
