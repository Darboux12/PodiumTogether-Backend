package com.podium.dal.repository;

import com.podium.dal.entity.Contact;
import com.podium.dal.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {

   Iterable<Contact> findAllByUserEmail(String email);

   Iterable<Contact> findAllBySubject(Subject subject);

}
