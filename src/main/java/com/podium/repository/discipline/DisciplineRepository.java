package com.podium.repository.discipline;

import com.podium.model.entity.discipline.Discipline;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DisciplineRepository extends CrudRepository<Discipline,Integer> {

    boolean existsByDiscipline(String disciplineName);

    Discipline findByDiscipline(String disciplineName);

    @Transactional
    void deleteByDiscipline(String discipline);

}
