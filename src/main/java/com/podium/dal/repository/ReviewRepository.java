package com.podium.dal.repository;

import com.podium.dal.entity.Place;
import com.podium.dal.entity.Review;
import com.podium.dal.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer> {

    boolean existsByAuthorAndAndPlace(User author, Place place);

    Optional<Review> findByAuthorAndPlace(User author, Place place);
}
