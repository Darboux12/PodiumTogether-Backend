package com.podium.service;

import com.podium.dal.entity.Contact;
import com.podium.dal.entity.Subject;
import com.podium.dal.entity.User;
import com.podium.dal.repository.ContactRepository;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import com.podium.service.dto.request.ContactAddServiceRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    private SubjectService subjectService;
    private SecurityService securityService;
    private UserService userService;

    public ContactService(ContactRepository contactRepository, SubjectService subjectService, SecurityService securityService, UserService userService) {
        this.contactRepository = contactRepository;
        this.subjectService = subjectService;
        this.securityService = securityService;
        this.userService = userService;
    }

    @Transactional
    public void addContact(ContactAddServiceRequest addServiceDto) throws PodiumEntityNotFoundException {
        this.contactRepository
                .save(this.convertServiceAddDtoToEntity(addServiceDto));
    }

    @Transactional
    public void deleteContact(int id, String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(!this.contactRepository.existsById(id))
            throw new PodiumEntityNotFoundException("Contact");

        this.contactRepository.deleteById(id);
    }

    public Iterable<Contact> findAllContact(String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        return this.contactRepository.findAll();
    }

    public Iterable<Contact> findAllContactByEmail(String email,String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        return this.contactRepository.findAllByUserEmail(email);
    }

    public Iterable<Contact> findAllContactBySubject(String subject,String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = userService.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        var subjectEntity = this.subjectService.findSubjectByName(subject);

        return this.contactRepository.findAllBySubject(subjectEntity);

    }

    private Contact convertServiceAddDtoToEntity(ContactAddServiceRequest addDto) throws PodiumEntityNotFoundException {

        Subject subject = this.subjectService.findSubjectByName(addDto.getSubject());

        return new Contact(
                addDto.getUserEmail(),
                addDto.getMessage(),
                subject
        );
    }

}
