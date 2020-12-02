package com.podium.repository;

import com.podium.model.entity.Country;
import com.podium.model.entity.Role;
import com.podium.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Iterable<User> findAllByRolesContaining(Role role);

    Iterable<User> findAllByCountry(Country country);

    @Transactional
    void deleteByUsername(String username);





}
