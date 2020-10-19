package com.podium.repository;

import com.podium.model.entity.Gender;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface GenderRepository extends CrudRepository<Gender,Integer> {

    boolean existsByGender(String genderName);

    Gender findByGender(String genderName);

    @Transactional
    void deleteByGender(String genderName);


}
