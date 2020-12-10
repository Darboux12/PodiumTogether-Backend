package com.podium.service;

import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.repository.ContactRepository;
import com.podium.service.exception.PodiumEntityNotFoundException;
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
    public void addContact(ContactRequestDto contactRequestDTO){
        this.contactRepository
                .save(this.convertContactRequestDtoToEntity(contactRequestDTO));
    }

    @Transactional
    public void deleteContact(int id){

        if(!this.contactRepository.existsById(id))
            throw new PodiumEntityNotFoundException("Contact");

        this.contactRepository.deleteById(id);
    }

    public Iterable<Contact> findAllContact(){
        return this.contactRepository.findAll();
    }

    public Iterable<Contact> findAllByEmail(String email){
        return this.contactRepository.findAllByUserEmail(email);
    }

    public Iterable<Contact> findAllBySubject(String subject){

        var subjectEntity = this.subjectService.findSubjectByName(subject);

        return this.contactRepository.findAllBySubject(subjectEntity);

    }

    public boolean existContactById(int id){
        return this.contactRepository.existsById(id);
    }

    private Contact convertContactRequestDtoToEntity(ContactRequestDto requestDto){

        return new Contact(
                requestDto.getUserEmail(),
                requestDto.getMessage(),
                this.subjectService.findSubjectByName(requestDto.getSubject())
        );

    }

}
