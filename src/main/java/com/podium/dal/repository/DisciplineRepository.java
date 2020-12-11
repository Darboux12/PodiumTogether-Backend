package com.podium.dal.repository;

import com.podium.dal.entity.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplineRepository extends CrudRepository<Discipline,Integer> {

    boolean existsByDiscipline(String disciplineName);

    Optional<Discipline> findByDiscipline(String disciplineName);

    void deleteByDiscipline(String discipline);

}
