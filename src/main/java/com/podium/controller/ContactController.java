package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.ContactAddControllerRequest;
import com.podium.controller.dto.response.ContactControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Contact;
import com.podium.service.ContactService;
import com.podium.service.dto.request.ContactAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
        this.contactService.addContact(ControllerRequestConverter.getInstance().convertContactAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("Contact request successfully sent");
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteContact)
    public ResponseEntity deleteContact(@PathVariable @PodiumValidVariable int id, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        this.contactService.deleteContact(id,authentication.getName());
        return ResponseEntity.ok().body("Contact successfully deleted");
    }

    // ADMIN
    @GetMapping(PodiumEndpoint.findAllContactByEmail)
    public ResponseEntity<Iterable<ContactControllerResponse>>
    findAllContactByEmail(@PathVariable @PodiumValidVariable String email,Authentication authentication) throws PodiumAuthorityException, PodiumEntityNotFoundException {
        var contacts = this.contactService.findAllContactByEmail(email,authentication.getName());
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertContactEntityIterableToResponseDto(contacts));
    }

    // ADMIN
    @GetMapping(PodiumEndpoint.findAllContactBySubject)
    public ResponseEntity<Iterable<ContactControllerResponse>> findAllContactBySubject(@PathVariable @PodiumValidVariable String subject,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        var contacts = this.contactService.findAllContactBySubject(subject,authentication.getName());
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertContactEntityIterableToResponseDto(contacts));
    }

    // ADMIN
    @GetMapping(PodiumEndpoint.findAllContact)
    public ResponseEntity<Iterable<ContactControllerResponse>> findAllContact(Authentication authentication) throws PodiumAuthorityException, PodiumEntityNotFoundException {
        var contacts = this.contactService.findAllContact(authentication.getName());
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertContactEntityIterableToResponseDto(contacts));
    }

}
