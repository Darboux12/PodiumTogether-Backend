package com.podium.dal.repository;

import com.podium.dal.entity.Ban;
import com.podium.dal.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BanRepository extends CrudRepository<Ban,Integer> {

    Optional<Ban> findAllByUser(User user);


}
