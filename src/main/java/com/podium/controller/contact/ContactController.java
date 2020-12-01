package com.podium.controller.contact;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.contact.ContactRequestDto;
import com.podium.model.dto.response.contact.ContactResponseDto;
import com.podium.service.contact.ContactService;
import com.podium.service.contact.SubjectService;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {

    private ContactService contactService;
    private SubjectService subjectService;

    public ContactController(ContactService contactService, SubjectService subjectService) {
        this.contactService = contactService;
        this.subjectService = subjectService;
    }

    @PostMapping(PodiumEndpoint.addContact)
    public ResponseEntity addContact(@RequestBody ContactRequestDto request){

        PodiumValidator.getInstance().validateRequestBody(request);

        if(!this.subjectService.existSubjectByName(request.getSubject()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given subject does not exist");

        this.contactService.addContact(request);

        return ResponseEntity.ok().body("Contact request successfully sent");

    }

    @DeleteMapping(PodiumEndpoint.deleteContact)
    public ResponseEntity deleteContact(@PathVariable int id){

        if(this.contactService.existContactById(id)){
            this.contactService.deleteContact(id);
            return ResponseEntity.ok().body("Contact deleted");
        }

        else throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Contact with given id does not exist");

    }

    @GetMapping(PodiumEndpoint.findAllContactByEmail)
    public ResponseEntity<Iterable<ContactResponseDto>>
    findAllContactByEmail(@PathVariable String email){

        return ResponseEntity
                .ok()
                .body(this.contactService.findAllByEmail(email));
    }

    @GetMapping(PodiumEndpoint.findAllContactBySubject)
    public ResponseEntity<Iterable<ContactResponseDto>>
    findAllContactBySubject(@PathVariable String subject){

        return ResponseEntity
                .ok()
                .body(this.contactService.findAllBySubject(subject));
    }

    @GetMapping(PodiumEndpoint.findAllContact)
    public ResponseEntity<Iterable<ContactResponseDto>>
    findAllContact(){
        return ResponseEntity
                .ok()
                .body(this.contactService.findAllContact());
    }

}
