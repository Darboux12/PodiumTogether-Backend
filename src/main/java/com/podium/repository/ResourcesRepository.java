package com.podium.repository;

import com.podium.model.entity.PodiumResource;
import org.springframework.data.repository.CrudRepository;

public interface ResourcesRepository extends CrudRepository<PodiumResource,Integer> {

    PodiumResource findByName(String resourceName);


}
