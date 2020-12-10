package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.City;
import com.podium.model.entity.Contact;
import com.podium.service.ContactService;
import com.podium.service.SubjectService;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity addContact(@RequestBody @PodiumValidBody ContactRequestDto request){
        this.contactService.addContact(request);
        return ResponseEntity.ok().body("Contact request successfully sent");
    }

    @DeleteMapping(PodiumEndpoint.deleteContact)
    public ResponseEntity deleteContact(@PathVariable @PodiumValidVariable int id){

        this.contactService.deleteContact(id);

        return ResponseEntity.ok().body("Contact successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllContactByEmail)
    public ResponseEntity<Iterable<ContactResponseDto>>
    findAllContactByEmail(@PathVariable @PodiumValidVariable String email){

        var contacts = this.contactService.findAllByEmail(email);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    @GetMapping(PodiumEndpoint.findAllContactBySubject)
    public ResponseEntity<Iterable<ContactResponseDto>> findAllContactBySubject(@PathVariable @PodiumValidVariable String subject){

        var contacts = this.contactService.findAllBySubject(subject);

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    @GetMapping(PodiumEndpoint.findAllContact)
    public ResponseEntity<Iterable<ContactResponseDto>> findAllContact(){

        var contacts = this.contactService.findAllContact();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(contacts));
    }

    private ContactResponseDto convertEntityToResponseDto(Contact contact){

        return new ContactResponseDto(
                contact.getId(),
                contact.getUserEmail(),
                contact.getMessage(),
                contact.getSubject().getSubject()
        );

    }

    private Iterable<ContactResponseDto> convertEntityIterableToResponseDto(Iterable<Contact> contacts){

        var contactResponses = new ArrayList<ContactResponseDto>();

        contacts.forEach(x -> contactResponses.add(this.convertEntityToResponseDto(x)));

        return contactResponses;
    }

}
