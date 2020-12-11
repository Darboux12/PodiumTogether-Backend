package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.SubjectAddRequest;
import com.podium.controller.dto.response.SubjectResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Subject;
import com.podium.service.SubjectService;
import com.podium.service.dto.SubjectAddServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity addSubject(@RequestBody @PodiumValidBody SubjectAddRequest request){
        this.subjectService.addSubject(this.convertAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @DeleteMapping(PodiumEndpoint.deleteSubject)
    public ResponseEntity deleteSubject(@PathVariable @PodiumValidVariable String name){
        this.subjectService.deleteSubjectByName(name);
        return ResponseEntity.ok().body("Subject successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllSubject)
    public ResponseEntity<Iterable<SubjectResponse>> findAllSubjects(){

        var subjects = this.subjectService.findAllSubjects();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(subjects));
    }

    @GetMapping("/subject/find/{name}")
    public ResponseEntity<SubjectResponse> findSubjectByName(@PathVariable @PodiumValidVariable String name){

        var subject= this.subjectService.findSubjectByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(subject));
    }

    @GetMapping(PodiumEndpoint.existSubjectByName)
    public ResponseEntity<Boolean> existSubjectByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.subjectService.existSubjectByName(name));
    }

    private SubjectAddServiceDto convertAddRequestToServiceDto(SubjectAddRequest request){
        return new SubjectAddServiceDto(request.getSubject());
    }

    private SubjectResponse convertEntityToResponseDto(Subject subject){

        return new SubjectResponse(subject.getSubject());

    }

    private Iterable<SubjectResponse> convertEntityIterableToResponseDto(Iterable<Subject> subjects){

        var subjectResponses = new ArrayList<SubjectResponse>();

        subjects.forEach(x -> subjectResponses.add(this.convertEntityToResponseDto(x)));

        return subjectResponses;
    }
}
