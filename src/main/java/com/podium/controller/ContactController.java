package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.ContactAddControllerRequest;
import com.podium.controller.dto.response.ContactControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Contact;
import com.podium.service.ContactService;
import com.podium.service.dto.request.ContactAddServiceDto;
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
    public ResponseEntity addContact(@RequestBody @PodiumValidBody ContactAddControllerRequest request) throws PodiumEntityNotFoundException {

        this.contactService.addContact(convertAddRequestToServiceDto(request));

        return ResponseEntity.ok().body("Contact request successfully sent");
    }

    @DeleteMapping(PodiumEndpoint.deleteContact)
    public ResponseEntity deleteContact(@PathVariable @PodiumValidVariable int id) throws PodiumEntityNotFoundException {

        this.contactService.deleteContact(id);

        return ResponseEntity.ok().body("Contact successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllContactByEmail)
    public ResponseEntity<Iterable<ContactControllerResponse>>
    findAllContactByEmail(@PathVariable @PodiumValidVariable String email){

        var contacts = this.contactService.findAllContactByEmail(email);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    @GetMapping(PodiumEndpoint.findAllContactBySubject)
    public ResponseEntity<Iterable<ContactControllerResponse>> findAllContactBySubject(@PathVariable @PodiumValidVariable String subject) throws PodiumEntityNotFoundException {

        var contacts = this.contactService.findAllContactBySubject(subject);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    @GetMapping(PodiumEndpoint.findAllContact)
    public ResponseEntity<Iterable<ContactControllerResponse>> findAllContact(){

        var contacts = this.contactService.findAllContact();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    private ContactAddServiceDto convertAddRequestToServiceDto(ContactAddControllerRequest request ){

        return new ContactAddServiceDto(
                request.getUserEmail(),
                request.getSubject(),
                request.getMessage()
        );
    }

    private ContactControllerResponse convertEntityToResponseDto(Contact contact){

        return new ContactControllerResponse(
                contact.getId(),
                contact.getUserEmail(),
                contact.getMessage(),
                contact.getSubject().getSubject()
        );

    }

    private Iterable<ContactControllerResponse> convertEntityIterableToResponseDto(Iterable<Contact> contacts){

        var contactResponses = new ArrayList<ContactControllerResponse>();

        contacts.forEach(x -> contactResponses.add(this.convertEntityToResponseDto(x)));

        return contactResponses;
    }

}
