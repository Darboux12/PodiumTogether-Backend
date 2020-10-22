package com.podium.service;

import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.dto.response.SubjectResponseDto;
import com.podium.model.entity.City;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.repository.ContactRepository;
import com.podium.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void addContact(ContactRequestDto contactRequestDTO){

        Contact contact = new Contact();

        contact.setUserEmail(contactRequestDTO.getUserEmail());
        contact.setMessage(contactRequestDTO.getMessage());

        Subject subject = this.subjectRepository.findBySubject(contactRequestDTO.getSubject());

        contact.setSubject(subject);

        this.contactRepository.save(contact);

    }

    public void deleteContact(int id){
        this.contactRepository.deleteById(id);
    }

    public void addSubject(String subjectName){

        Subject subject = new Subject();

        subject.setSubject(subjectName);

        this.subjectRepository.save(subject);

    }

    public boolean existSubjectByName(String subjectName){
       return this.subjectRepository.existsBySubject(subjectName);
    }

    public void deleteSubjectByName(String name){
        this.subjectRepository.deleteBySubject(name);
    }

    public Iterable<SubjectResponseDto> findAllSubjects(){

        List<SubjectResponseDto> responseDtos = new ArrayList<>();

        for(Subject subject : this.subjectRepository.findAll())
            responseDtos.add(this.convertSubjectEntityToResponseDto(subject));

        return responseDtos;
    }

    public Iterable<ContactResponseDto> findAllContact(){

        List<ContactResponseDto> responseDtos = new ArrayList<>();

        for(Contact contact : this.contactRepository.findAll())
            responseDtos.add(this.convertContactEntityToResponseDto(contact));

        return responseDtos;
    }

    public Iterable<ContactResponseDto> findAllByEmail(String email){

        List<ContactResponseDto> dtos = new ArrayList<>();

        for(Contact contact : this.contactRepository.findAllByUserEmail(email))
            dtos.add(this.convertContactEntityToResponseDto(contact));

        return dtos;

    }

    public Iterable<ContactResponseDto> findAllBySubject(String subject){

        Subject sub = this.subjectRepository.findBySubject(subject);

        List<ContactResponseDto> dtos = new ArrayList<>();

        for(Contact contact : this.contactRepository.findAllBySubject(sub))
            dtos.add(this.convertContactEntityToResponseDto(contact));

        return dtos;

    }

    public SubjectResponseDto findSubjectByName(String subjectName){
        return this.convertSubjectEntityToResponseDto
                (this.subjectRepository.findBySubject(subjectName));
    }

    private Contact convertContactRequestDtoToEntity(ContactRequestDto requestDto){

        Contact contact = new Contact();
        Subject subject = this.subjectRepository.findBySubject(requestDto.getSubject());
        contact.setSubject(subject);
        contact.setUserEmail(requestDto.getUserEmail());
        contact.setMessage(requestDto.getMessage());

        return contact;

    }

    private ContactResponseDto convertContactEntityToResponseDto(Contact contact){

        ContactResponseDto responseDto = new ContactResponseDto();
        responseDto.setEmail(contact.getUserEmail());
        responseDto.setId(contact.getContactId());
        responseDto.setMessage(contact.getMessage());
        responseDto.setSubject(contact.getSubject().getSubject());
        return responseDto;

    }

    private Subject convertSubjectRequestDtoToEntity(SubjectRequestDto requestDto){

        Subject subject = new Subject();
        subject.setSubject(requestDto.getSubject());
        return subject;

    }

    private SubjectResponseDto convertSubjectEntityToResponseDto(Subject subject){

        SubjectResponseDto responseDto = new SubjectResponseDto();
        responseDto.setSubject(subject.getSubject());
        return responseDto;


    }
}
