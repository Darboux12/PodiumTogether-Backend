package com.podium.dal.repository;

import com.podium.dal.entity.PodiumResource;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ResourceRepository extends CrudRepository<PodiumResource,Integer> {

    PodiumResource findByName(String resourceName);

    boolean existsById(int id);

}
