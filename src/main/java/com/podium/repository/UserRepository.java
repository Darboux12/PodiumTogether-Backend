package com.podium.repository;

import com.podium.model.entity.Country;
import com.podium.model.entity.Role;
import com.podium.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Iterable<User> findAllByRolesContaining(Role role);

    Iterable<User> findAllByCountry(Country country);

    @Modifying(clearAutomatically = true)
    @Query("update User user set user.username =:newUsername where user.username =:oldUsername")
    void updateUserUsername
            (@Param("oldUsername") String oldUsername,
             @Param("newUsername") String newUsername);

    @Transactional
    void deleteByUsername(String username);






}
