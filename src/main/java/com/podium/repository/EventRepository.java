package com.podium.repository;

import com.podium.model.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EventRepository extends CrudRepository<Event,Integer> {

    boolean existsByTitle(String eventTitle);

    Event findByTitle(String title);

    @Transactional
    void deleteByTitle(String eventTitle);


}
