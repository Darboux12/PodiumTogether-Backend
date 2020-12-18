package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.ContactAddRequest;
import com.podium.controller.dto.response.ContactResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Contact;
import com.podium.service.ContactService;
import com.podium.service.dto.ContactAddServiceDto;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping(PodiumEndpoint.addContact)
    public ResponseEntity addContact(@RequestBody @PodiumValidBody ContactAddRequest request) throws PodiumEntityNotFoundException {

        this.contactService.addContact(convertAddRequestToServiceDto(request));

        return ResponseEntity.ok().body("Contact request successfully sent");
    }

    @DeleteMapping(PodiumEndpoint.deleteContact)
    public ResponseEntity deleteContact(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        this.contactService.deleteContact(id);

        return ResponseEntity.ok().body("Contact successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllContactByEmail)
    public ResponseEntity<Iterable<ContactResponse>>
    findAllContactByEmail(@PathVariable @PodiumValidVariable String email){

        var contacts = this.contactService.findAllContactByEmail(email);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    @GetMapping(PodiumEndpoint.findAllContactBySubject)
    public ResponseEntity<Iterable<ContactResponse>> findAllContactBySubject(@PathVariable @PodiumValidVariable String subject) throws PodiumEntityNotFoundException {

        var contacts = this.contactService.findAllContactBySubject(subject);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    @GetMapping(PodiumEndpoint.findAllContact)
    public ResponseEntity<Iterable<ContactResponse>> findAllContact(){

        var contacts = this.contactService.findAllContact();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    private ContactAddServiceDto convertAddRequestToServiceDto(ContactAddRequest request ){

        return new ContactAddServiceDto(
                request.getUserEmail(),
                request.getMessage(),
                request.getSubject()
        );
    }

    private ContactResponse convertEntityToResponseDto(Contact contact){

        return new ContactResponse(
                contact.getId(),
                contact.getUserEmail(),
                contact.getMessage(),
                contact.getSubject().getSubject()
        );

    }

    private Iterable<ContactResponse> convertEntityIterableToResponseDto(Iterable<Contact> contacts){

        var contactResponses = new ArrayList<ContactResponse>();

        contacts.forEach(x -> contactResponses.add(this.convertEntityToResponseDto(x)));

        return contactResponses;
    }

}
