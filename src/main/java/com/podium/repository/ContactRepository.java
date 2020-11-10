package com.podium.repository;

import com.podium.model.entity.contact.Contact;
import com.podium.model.entity.contact.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {

   Iterable<Contact> findAllByUserEmail(String email);

   Iterable<Contact> findAllBySubject(Subject subject);

}
