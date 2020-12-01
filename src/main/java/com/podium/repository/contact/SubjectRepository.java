package com.podium.repository.contact;

import com.podium.model.entity.contact.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer> {

    Optional<Subject> findBySubject(String subjectName);

    boolean existsBySubject(String subjectName);

    @Transactional
    void deleteBySubject(String name);

}
