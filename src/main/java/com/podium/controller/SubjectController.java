package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.SubjectRequestDto;
import com.podium.model.dto.response.SubjectResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.Subject;
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
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping(PodiumEndpoint.addSubject)
    public ResponseEntity addSubject(@RequestBody @PodiumValidBody SubjectRequestDto request){
        this.subjectService.addSubject(request);
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @DeleteMapping(PodiumEndpoint.deleteSubject)
    public ResponseEntity deleteSubject(@PathVariable @PodiumValidVariable String name){
        this.subjectService.deleteSubjectByName(name);
        return ResponseEntity.ok().body("Subject successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllSubject)
    public ResponseEntity<Iterable<SubjectResponseDto>> findAllSubjects(){

        var subjects = this.subjectService.findAllSubjects();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(subjects));
    }

    @GetMapping("/subject/find/{name}")
    public ResponseEntity<SubjectResponseDto> findSubjectByName(@PathVariable @PodiumValidVariable String name){

        var subject= this.subjectService.findSubjectByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(subject));
    }

    @GetMapping(PodiumEndpoint.existSubjectByName)
    public ResponseEntity<Boolean> existSubjectByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.subjectService.existSubjectByName(name));
    }

    private SubjectResponseDto convertEntityToResponseDto(Subject subject){

        return new SubjectResponseDto(subject.getSubject());

    }

    private Iterable<SubjectResponseDto> convertEntityIterableToResponseDto(Iterable<Subject> subjects){

        var subjectResponses = new ArrayList<SubjectResponseDto>();

        subjects.forEach(x -> subjectResponses.add(this.convertEntityToResponseDto(x)));

        return subjectResponses;
    }
}
