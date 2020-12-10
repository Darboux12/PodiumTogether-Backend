package com.podium.repository;

import com.podium.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    boolean existsByRole(String roleName);

    Optional<Role> findByRole(String roleName);



}
