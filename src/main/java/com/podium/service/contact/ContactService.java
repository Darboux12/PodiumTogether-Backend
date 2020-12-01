package com.podium.service.contact;

import com.podium.model.dto.request.contact.ContactRequestDto;
import com.podium.model.dto.response.contact.ContactResponseDto;
import com.podium.model.entity.contact.Contact;
import com.podium.model.entity.contact.Subject;
import com.podium.repository.contact.ContactRepository;
import com.podium.repository.contact.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private SubjectRepository subjectRepository;

    public ContactService(ContactRepository contactRepository, SubjectRepository subjectRepository) {
        this.contactRepository = contactRepository;
        this.subjectRepository = subjectRepository;
    }

    public void addContact(ContactRequestDto contactRequestDTO){

        this.contactRepository
                .save(this.convertContactRequestDtoToEntity(contactRequestDTO));

    }

    public void deleteContact(int id){
        this.contactRepository.deleteById(id);
    }

    public Iterable<ContactResponseDto> findAllContact(){

        List<ContactResponseDto> responseDtos = new ArrayList<>();

        this.contactRepository
                .findAll()
                .forEach(x -> responseDtos.add(
                        this.convertContactEntityToResponseDto(x)
                ));

        return responseDtos;
    }

    public Iterable<ContactResponseDto> findAllByEmail(String email){

        List<ContactResponseDto> responseDtos = new ArrayList<>();

        this.contactRepository
                .findAllByUserEmail(email)
                .forEach(x -> responseDtos.add(
                        this.convertContactEntityToResponseDto(x)
                ));

        return responseDtos;

    }

    public Iterable<ContactResponseDto> findAllBySubject(String subject){

        List<ContactResponseDto> responseDtos = new ArrayList<>();

        this.contactRepository
                .findAllBySubject(this.findSubjectEntity(subject))
                .forEach(x -> responseDtos.add(
                        this.convertContactEntityToResponseDto(x)
                ));

        return responseDtos;

    }

    public boolean existContactById(int id){
        return this.contactRepository.existsById(id);
    }

    private Contact convertContactRequestDtoToEntity(ContactRequestDto requestDto){

        return new Contact(
                requestDto.getUserEmail(),
                requestDto.getMessage(),
                this.findSubjectEntity(requestDto.getSubject())
        );

    }

    private ContactResponseDto convertContactEntityToResponseDto(Contact contact){

        return new ContactResponseDto(
                contact.getContactId(),
                contact.getUserEmail(),
                contact.getMessage(),
                contact.getSubject().getSubject()
        );

    }

    private Subject findSubjectEntity(String subject){
        return this.subjectRepository.findBySubject(subject).orElse(null);
    }

}
