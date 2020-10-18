package com.podium.controller;

import com.podium.model.entity.Subject;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.service.ContactService;
import com.podium.validation.PodiumValidator;
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

    @PostMapping("/contact/add")
    public ResponseEntity addContact(@RequestBody ContactRequestDto request){

        PodiumValidator.getInstance().validateRequestBody(request);

        if(!this.contactService.existSubjectByName(request.getSubject()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Given subject does not exist");

        this.contactService.addContact(request);

        return ResponseEntity.ok().body("Contact request successfully sent");

    }

    @DeleteMapping("/contact/delete/{id}")
    public ResponseEntity deleteContact(@PathVariable int id){

        this.contactService.deleteContact(id);
        return ResponseEntity.ok().body("Contact deleted");
    }

    @GetMapping("/contact/find")
    public ResponseEntity findContact(
            @RequestParam String userEmail,
            @RequestParam String message,
            @RequestParam String subject){

        return ResponseEntity
                .ok()
                .body(this.contactService.findContact(userEmail,message,subject));
    }

    @PostMapping("/subject/add")
    public ResponseEntity addSubject(@RequestBody SubjectRequestDto request){

        PodiumValidator.getInstance().validateRequestBody(request);

        if(this.contactService.existSubjectByName(request.getSubject()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Subject already exists");

        this.contactService.addSubject(request.getSubject());
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @DeleteMapping("/subject/delete/{name}")
    public ResponseEntity deleteSubject(@PathVariable String name){

        this.contactService.deleteSubjectByName(name);
        return ResponseEntity.ok().body("Subject deleted");
    }

    @GetMapping("/subject/find/all")
    public Iterable<Subject> findAllSubjects(){
        return this.contactService.findAllSubjects();
    }

    @GetMapping("/subject/find/{name}")
    public ResponseEntity findSubjectByName(@PathVariable String name){
        return ResponseEntity
                .ok()
                .body(this.contactService.findSubjectByName(name));
    }

}
