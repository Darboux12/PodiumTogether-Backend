package com.podium.repository;

import com.podium.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    boolean existsByName(String roleName);

    Role findByName(String roleName);



}
