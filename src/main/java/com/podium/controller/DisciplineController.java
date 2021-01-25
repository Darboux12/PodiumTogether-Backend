package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.DisciplineAddControllerRequest;
import com.podium.controller.dto.response.DisciplineControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Discipline;
import com.podium.service.DisciplineService;
import com.podium.service.dto.request.DisciplineAddServiceRequest;
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
public class DisciplineController {

    private DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping(PodiumEndpoint.findAllDiscipline)
    public ResponseEntity<Iterable<DisciplineControllerResponse>> findAllDiscipline(){
        var disciplines = this.disciplineService.findAllDiscipline();
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertDisciplineEntityIterableToResponseDto(disciplines));
    }

    @GetMapping(PodiumEndpoint.findByDisciplineName)
    public ResponseEntity<DisciplineControllerResponse> findDisciplineByName(@PathVariable @PodiumValidVariable String discipline) throws PodiumEntityNotFoundException {
        var dis = this.disciplineService.findDisciplineByName(discipline);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertDisciplineEntityToResponseDto(dis));
    }

    // ADMIN
    @PostMapping(PodiumEndpoint.addDiscipline)
    public ResponseEntity addDiscipline(@RequestBody @PodiumValidBody DisciplineAddControllerRequest requestDto, Authentication authentication) throws PodiumEntityAlreadyExistException, PodiumAuthorityException, PodiumEntityNotFoundException {
        this.disciplineService.addDiscipline(ControllerRequestConverter.getInstance().convertDisciplineAddRequestToServiceModel(requestDto),authentication.getName());
        return ResponseEntity.ok().body("Discipline successfully added");

    }

    @GetMapping(PodiumEndpoint.existDisciplineByName)
    public ResponseEntity<Boolean> existDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        return ResponseEntity.ok().body(this.disciplineService.existDisciplineByName(discipline));
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteDisciplineByName)
    public ResponseEntity deleteDisciplineByName(@PathVariable @PodiumValidVariable String discipline, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        this.disciplineService.deleteDisciplineByName(discipline,authentication.getName());
        return ResponseEntity.ok().body("Discipline successfully deleted");
    }

}
