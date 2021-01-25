package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.SubjectAddControllerRequest;
import com.podium.controller.dto.response.SubjectControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Subject;
import com.podium.service.SubjectService;
import com.podium.service.dto.request.SubjectAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    // ADMIN
    @PostMapping(PodiumEndpoint.addSubject)
    public ResponseEntity addSubject(@RequestBody @PodiumValidBody SubjectAddControllerRequest request, Authentication authentication) throws PodiumEntityAlreadyExistException, PodiumAuthorityException, PodiumEntityNotFoundException {
        this.subjectService.addSubject(ControllerRequestConverter.getInstance().convertSubjectAddRequestToServiceDto(request),authentication.getName());
        return ResponseEntity.ok().body("Subject successfully added");
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteSubject)
    public ResponseEntity deleteSubject(@PathVariable @PodiumValidVariable String name,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        this.subjectService.deleteSubjectByName(name,authentication.getName());
        return ResponseEntity.ok().body("Subject successfully deleted");
    }

    @GetMapping(PodiumEndpoint.findAllSubject)
    public ResponseEntity<Iterable<SubjectControllerResponse>> findAllSubjects(){
        var subjects = this.subjectService.findAllSubjects();
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertSubjectEntityIterableToResponseDto(subjects));
    }

    @GetMapping(PodiumEndpoint.findSubjectByName)
    public ResponseEntity<SubjectControllerResponse> findSubjectByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {
        var subject= this.subjectService.findSubjectByName(name);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertSubjectEntityToResponseDto(subject));
    }

    @GetMapping(PodiumEndpoint.existSubjectByName)
    public ResponseEntity<Boolean> existSubjectByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.subjectService.existSubjectByName(name));
    }




}
