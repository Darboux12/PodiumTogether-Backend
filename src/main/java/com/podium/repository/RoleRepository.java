package com.podium.repository;

import com.podium.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    boolean existsByRole(String roleName);

    Role findByRole(String roleName);



}
