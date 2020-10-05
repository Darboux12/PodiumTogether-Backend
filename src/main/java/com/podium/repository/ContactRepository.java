package com.podium.repository;

import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {



}
