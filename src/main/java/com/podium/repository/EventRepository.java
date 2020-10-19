package com.podium.repository;

import com.podium.model.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event,Integer> {

    boolean existsByTitle(String eventTitle);


}
