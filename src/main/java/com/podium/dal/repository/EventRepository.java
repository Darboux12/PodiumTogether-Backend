package com.podium.dal.repository;

import com.podium.dal.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event,Integer> {

    boolean existsByTitle(String eventTitle);

    Optional<Event> findByTitle(String title);




}
