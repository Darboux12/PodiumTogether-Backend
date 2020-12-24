package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.DisciplineAddControllerRequest;
import com.podium.controller.dto.response.DisciplineControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Discipline;
import com.podium.service.DisciplineService;
import com.podium.service.dto.request.DisciplineAddServiceRequest;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
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

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(disciplines));
    }

    @GetMapping(PodiumEndpoint.findByDisciplineName)
    public ResponseEntity<DisciplineControllerResponse> findDisciplineByName(@PathVariable @PodiumValidVariable String discipline) throws PodiumEntityNotFoundException {

        var dis = this.disciplineService.findDisciplineByName(discipline);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(dis));
    }

    @PostMapping(PodiumEndpoint.addDiscipline)
    public ResponseEntity addDiscipline(@RequestBody @PodiumValidBody DisciplineAddControllerRequest requestDto) throws PodiumEntityAlreadyExistException {
        this.disciplineService.addDiscipline(this.convertAddRequestToServiceModel(requestDto));
        return ResponseEntity.ok().body("Discipline successfully added");

    }

    @GetMapping(PodiumEndpoint.existDisciplineByName)
    public ResponseEntity<Boolean> existDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        return ResponseEntity.ok().body(this.disciplineService.existDisciplineByName(discipline));
    }

    @DeleteMapping(PodiumEndpoint.deleteDisciplineByName)
    public ResponseEntity deleteDisciplineByName(@PathVariable @PodiumValidVariable String discipline) throws PodiumEntityNotFoundException {
        this.disciplineService.deleteDisciplineByName(discipline);
        return ResponseEntity.ok().body("Discipline successfully deleted");
    }

    private DisciplineControllerResponse convertEntityToResponseDto(Discipline discipline){
        return new DisciplineControllerResponse(discipline.getDiscipline());
    }

    private Iterable<DisciplineControllerResponse> convertEntityIterableToResponseDto(Iterable<Discipline> disciplines){

        var disciplineResponses = new ArrayList<DisciplineControllerResponse>();

        disciplines.forEach(x -> disciplineResponses.add(this.convertEntityToResponseDto(x)));

        return disciplineResponses;
    }

    private DisciplineAddServiceRequest convertAddRequestToServiceModel(DisciplineAddControllerRequest requestDto){
        return new DisciplineAddServiceRequest(requestDto.getDiscipline());
    }

}
