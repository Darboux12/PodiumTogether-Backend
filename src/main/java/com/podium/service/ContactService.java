package com.podium.service;

import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.model.request.ContactRequest;
import com.podium.repository.ContactRepository;
import com.podium.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, SubjectRepository subjectRepository) {
        this.contactRepository = contactRepository;
        this.subjectRepository = subjectRepository;
    }

    public void addContact(ContactRequest contactRequest){

        Contact contact = new Contact();

        contact.setUserEmail(contactRequest.getUserEmail());
        contact.setMessage(contactRequest.getMessage());

        Subject subject = this.subjectRepository.findByName(contactRequest.getSubject());

        contact.setSubject(subject);

        this.contactRepository.save(contact);

    }

    public void deleteContact(int id){
        this.contactRepository.deleteById(id);
    }

    public Contact findContact(String userEmail, String message, String subject){

        Subject subjectEntity = this.subjectRepository.findByName(subject);

        return this.contactRepository.findByUserEmailAndMessageAndSubject(userEmail,message,subjectEntity);
    }

    public void addSubject(String subjectName){

        Subject subject = new Subject();

        subject.setName(subjectName);

        this.subjectRepository.save(subject);



    }

    public boolean existSubjectByName(String subjectName){
       return this.subjectRepository.existsByName(subjectName);
    }

    public void deleteSubjectByName(String name){
        this.subjectRepository.deleteByName(name);
    }

    public Iterable<Subject> findAllSubjects(){
        return this.subjectRepository.findAll();
    }

    public Subject findSubjectByName(String subjectName){
        return this.subjectRepository.findByName(subjectName);
    }
}
