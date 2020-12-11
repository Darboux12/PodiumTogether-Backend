package com.podium.dal.repository;

import com.podium.dal.entity.Country;
import com.podium.dal.entity.Role;
import com.podium.dal.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Iterable<User> findAllByRolesContaining(Role role);

    Iterable<User> findAllByCountry(Country country);

    void deleteByUsername(String username);
}
