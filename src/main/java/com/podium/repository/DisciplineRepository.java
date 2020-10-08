package com.podium.repository;

import com.podium.model.entity.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplineRepository extends CrudRepository<Discipline,Integer> {

    boolean existsByName(String disciplineName);

    Discipline findByName(String disciplineName);

}
