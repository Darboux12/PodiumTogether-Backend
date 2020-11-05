package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.ContactRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.dto.response.SubjectResponseDto;
import com.podium.model.entity.Subject;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.service.ContactService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping(PodiumEndpoint.addContact)
    public ResponseEntity addContact(@RequestBody ContactRequestDto request){

        PodiumValidator.getInstance().validateRequestBody(request);

        if(!this.contactService.existSubjectByName(request.getSubject()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given subject does not exist");

        this.contactService.addContact(request);

        return ResponseEntity.ok().body("Contact request successfully sent");

    }

    @DeleteMapping(PodiumEndpoint.deleteContact)
    public ResponseEntity deleteContact(@PathVariable int id){

        this.contactService.deleteContact(id);
        return ResponseEntity.ok().body("Contact deleted");
    }

    @GetMapping(PodiumEndpoint.findAllContactByEmail)
    public ResponseEntity findContactByEmail(@PathVariable String email){

        return ResponseEntity
                .ok()
                .body(this.contactService.findAllByEmail(email));
    }

    @GetMapping(PodiumEndpoint.findAllContactBySubject)
    public ResponseEntity findContactBySubject(@PathVariable String subject){

        return ResponseEntity
                .ok()
                .body(this.contactService.findAllBySubject(subject));
    }

    @PostMapping(PodiumEndpoint.addSubject)
    public ResponseEntity addSubject(@RequestBody SubjectRequestDto request){

        PodiumValidator.getInstance().validateRequestBody(request);

        if(this.contactService.existSubjectByName(request.getSubject()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Subject already exists");

        this.contactService.addSubject(request.getSubject());
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @DeleteMapping(PodiumEndpoint.deleteSubject)
    public ResponseEntity deleteSubject(@PathVariable String name){

        this.contactService.deleteSubjectByName(name);
        return ResponseEntity.ok().body("Subject deleted");
    }

    @GetMapping(PodiumEndpoint.findAllSubject)
    public Iterable<SubjectResponseDto> findAllSubjects(){
        return this.contactService.findAllSubjects();
    }

    @GetMapping(PodiumEndpoint.findAllContact)
    public Iterable<ContactResponseDto> findAllContact(){
        return this.contactService.findAllContact();
    }

    @GetMapping("/subject/find/{name}")
    public ResponseEntity findSubjectByName(@PathVariable String name){
        return ResponseEntity
                .ok()
                .body(this.contactService.findSubjectByName(name));
    }

    @GetMapping(PodiumEndpoint.existSubjectByName)
    public ResponseEntity existSubjectByName(@PathVariable String name){

        if(this.contactService.existSubjectByName(name))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.notFound().build();

    }




}
