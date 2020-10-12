package com.podium.repository;

import com.podium.model.entity.PodiumResource;
import org.springframework.data.repository.CrudRepository;

public interface ResourceRepository extends CrudRepository<PodiumResource,Integer> {

    PodiumResource findByName(String resourceName);


}
