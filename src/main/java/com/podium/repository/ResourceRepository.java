package com.podium.repository;

import com.podium.model.entity.resource.PodiumResource;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ResourceRepository extends CrudRepository<PodiumResource,Integer> {

    PodiumResource findByName(String resourceName);

    @Transactional
    void deleteByResourceId(int id);

    boolean existsByResourceId(int id);


}
