package com.podium.dal.repository;

import com.podium.dal.entity.RatingCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingCategoryRepository extends CrudRepository<RatingCategory,Integer> {

    Optional<RatingCategory> findByCategory(String category);

    boolean existsByCategory(String category);

    void deleteByCategory(String category);

}
