package com.podium.repository;

import com.podium.model.entity.Gender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends CrudRepository<Gender,Integer> {

    boolean existsByGender(String genderName);

    Optional<Gender> findByGender(String genderName);

    void deleteByGender(String genderName);

}
