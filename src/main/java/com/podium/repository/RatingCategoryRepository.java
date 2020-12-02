package com.podium.repository;

import com.podium.model.entity.RatingCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RatingCategoryRepository extends CrudRepository<RatingCategory,Integer> {

    Iterable<RatingCategory> findAllByCategory(String category);

    RatingCategory findByCategory(String category);

    boolean existsByCategory(String category);

    @Transactional
    void deleteByCategory(String category);

}
