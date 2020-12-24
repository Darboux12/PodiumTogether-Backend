package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.SubjectAddControllerRequest;
import com.podium.controller.dto.response.SubjectControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Subject;
import com.podium.service.SubjectService;
import com.podium.service.dto.request.SubjectAddServiceRequest;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
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
    public ResponseEntity addSubject(@RequestBody @PodiumValidBody SubjectAddControllerRequest request) throws PodiumEntityAlreadyExistException {
        this.subjectService.addSubject(this.convertAddRequestToServiceDto(request));
        return ResponseEntity.ok().body("Subject successfully added");

    }

    @DeleteMapping(PodiumEndpoint.deleteSubject)
    public ResponseEntity deleteSubject(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {
        this.subjectService.deleteSubjectByName(name);
        return ResponseEntity.ok().body("Subject successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllSubject)
    public ResponseEntity<Iterable<SubjectControllerResponse>> findAllSubjects(){

        var subjects = this.subjectService.findAllSubjects();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(subjects));
    }

    @GetMapping("/subject/find/{name}")
    public ResponseEntity<SubjectControllerResponse> findSubjectByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        var subject= this.subjectService.findSubjectByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(subject));
    }

    @GetMapping(PodiumEndpoint.existSubjectByName)
    public ResponseEntity<Boolean> existSubjectByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.subjectService.existSubjectByName(name));
    }

    private SubjectAddServiceRequest convertAddRequestToServiceDto(SubjectAddControllerRequest request){
        return new SubjectAddServiceRequest(request.getSubject());
    }

    private SubjectControllerResponse convertEntityToResponseDto(Subject subject){

        return new SubjectControllerResponse(subject.getSubject());

    }

    private Iterable<SubjectControllerResponse> convertEntityIterableToResponseDto(Iterable<Subject> subjects){

        var subjectResponses = new ArrayList<SubjectControllerResponse>();

        subjects.forEach(x -> subjectResponses.add(this.convertEntityToResponseDto(x)));

        return subjectResponses;
    }
}
