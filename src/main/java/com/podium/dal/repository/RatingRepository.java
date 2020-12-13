package com.podium.dal.repository;

import com.podium.dal.entity.RatingCategory;
import com.podium.dal.entity.StarRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<StarRating,Integer> {

    Optional<StarRating> findByCategoryAndRating(RatingCategory category, int rating);

    boolean existsByCategoryAndRating(RatingCategory category, int rating);

}
