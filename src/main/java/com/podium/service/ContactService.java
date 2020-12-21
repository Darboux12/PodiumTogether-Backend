package com.podium.service;

import com.podium.dal.entity.Contact;
import com.podium.dal.entity.Subject;
import com.podium.dal.repository.ContactRepository;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.dto.request.ContactAddServiceDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    private SubjectService subjectService;

    public ContactService(ContactRepository contactRepository, SubjectService subjectService) {
        this.contactRepository = contactRepository;
        this.subjectService = subjectService;
    }

    @Transactional
    public void addContact(ContactAddServiceDto addServiceDto) throws PodiumEntityNotFoundException {
        this.contactRepository
                .save(this.convertServiceAddDtoToEntity(addServiceDto));
    }

    @Transactional
    public void deleteContact(int id) throws PodiumEntityNotFoundException {

        if(!this.contactRepository.existsById(id))
            throw new PodiumEntityNotFoundException("Contact");

        this.contactRepository.deleteById(id);
    }

    public Iterable<Contact> findAllContact(){
        return this.contactRepository.findAll();
    }

    public Iterable<Contact> findAllContactByEmail(String email){
        return this.contactRepository.findAllByUserEmail(email);
    }

    public Iterable<Contact> findAllContactBySubject(String subject) throws PodiumEntityNotFoundException {

        var subjectEntity = this.subjectService.findSubjectByName(subject);

        return this.contactRepository.findAllBySubject(subjectEntity);

    }

    public boolean existContactById(int id){
        return this.contactRepository.existsById(id);
    }

    private Contact convertServiceAddDtoToEntity(ContactAddServiceDto addDto) throws PodiumEntityNotFoundException {

        Subject subject = this.subjectService.findSubjectByName(addDto.getSubject());

        return new Contact(
                addDto.getUserEmail(),
                addDto.getMessage(),
                subject
        );
    }

}
