package com.podium.dal.repository;

import com.podium.dal.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer> {

    Optional<Subject> findBySubject(String subjectName);

    boolean existsBySubject(String subjectName);

    void deleteBySubject(String name);

}
