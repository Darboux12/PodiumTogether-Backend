package com.podium.repository;

import com.podium.model.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer> {

    Subject findByName(String subjectName);

    boolean existsByName(String subjectName);

}
