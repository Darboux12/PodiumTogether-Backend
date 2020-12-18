package com.podium.dal.repository;

import com.podium.dal.entity.Place;
import com.podium.dal.entity.Review;
import com.podium.dal.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Integer> {

    boolean existsByAuthorAndPlace(User author, Place place);

    Iterable<Review> findByAuthor(User author);

    @Query(value = "UPDATE Review R SET R.likes = R.likes + 1 where R.id = ?1")
    @Modifying
    void incrementLikesCountById(int id);

    @Query(value = "UPDATE Review R SET R.dislikes = R.dislikes + 1 where R.id = ?1")
    @Modifying
    void incrementDislikesCountById(int id);
}
