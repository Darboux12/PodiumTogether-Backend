package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.contact.SubjectRequestDto;
import com.podium.model.dto.response.contact.SubjectResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.service.SubjectService;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SubjectController {

    private SubjectService subjectService;
    private PodiumDtoValidator dtoValidator;


    public SubjectController(SubjectService subjectService, PodiumDtoValidator podiumDtoValidator) {
        this.subjectService = subjectService;
        this.dtoValidator = podiumDtoValidator;
    }

    @PostMapping(PodiumEndpoint.addSubject)
    public ResponseEntity addSubject(@RequestBody SubjectRequestDto request){


        dtoValidator.validateRequestBody(request);

        if(this.subjectService.existSubjectByName(request.getSubject()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Subject already exists");

        this.subjectService.addSubject(request);
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @DeleteMapping(PodiumEndpoint.deleteSubject)
    public ResponseEntity deleteSubject(@PathVariable String name){

        this.subjectService.deleteSubjectByName(name);
        return ResponseEntity.ok().body("Subject deleted");
    }

    @GetMapping(PodiumEndpoint.findAllSubject)
    public ResponseEntity<Iterable<SubjectResponseDto>> findAllSubjects(){

        return ResponseEntity
                .ok()
                .body( this.subjectService.findAllSubjects());

    }

    @GetMapping("/subject/find/{name}")
    public ResponseEntity findSubjectByName(@PathVariable String name){

        System.out.println("Hej");

        return ResponseEntity
                .ok()
                .body(this.subjectService.findSubjectByName(name));
    }

    @GetMapping(PodiumEndpoint.existSubjectByName)
    public ResponseEntity existSubjectByName(@PathVariable String name){

        if(this.subjectService.existSubjectByName(name))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.notFound().build();

    }

}
