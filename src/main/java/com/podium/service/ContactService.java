package com.podium.service;

import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.repository.ContactRepository;
import com.podium.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, SubjectRepository subjectRepository) {
        this.contactRepository = contactRepository;
        this.subjectRepository = subjectRepository;
    }

    public void addContact(ContactRequestDto contactRequestDTO){

        Contact contact = new Contact();

        contact.setUserEmail(contactRequestDTO.getUserEmail());
        contact.setMessage(contactRequestDTO.getMessage());

        Subject subject = this.subjectRepository.findByName(contactRequestDTO.getSubject());

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
