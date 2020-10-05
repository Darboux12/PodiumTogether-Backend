package com.podium.controller;

import com.podium.model.entity.Subject;
import com.podium.model.request.ContactRequest;
import com.podium.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contact/add")
    public ResponseEntity addContact(@RequestBody ContactRequest request){

        if(request.getUserEmail().isEmpty()){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Email");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Email cannot be empty");

        }

        if(request.getSubject().isEmpty()){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Subject");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Subject cannot be empty");

        }

        if(request.getMessage().isEmpty()){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Message");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Message cannot be empty");

        }

        if(!this.contactService.existSubjectByName(request.getSubject())){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Impossible value","Subject");
            return ResponseEntity
                    .status(500)
                    .headers(headers)
                    .body("Unreachable value was send");

        }

        this.contactService.addContact(request);

        return ResponseEntity.ok().body("Contact request successfully sent");

    }

    @PostMapping("/subject/add")
    public ResponseEntity addSubject(@RequestParam("subject") String subject){

        if(subject.isEmpty()){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Subject");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Subject cannot be empty");

        }

        if(this.contactService.existSubjectByName(subject)){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Already-Exist","Subject");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Subject already exist");
        }

        this.contactService.addSubject(subject);
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @GetMapping("/subject/get/all")
    public Iterable<Subject> findAllSubjects(){
        return this.contactService.findAllSubjects();
    }
}
