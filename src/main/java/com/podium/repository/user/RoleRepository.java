package com.podium.repository.user;

import com.podium.model.entity.user.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Integer> {

    boolean existsByRole(String roleName);

    Role findByRole(String roleName);



}
