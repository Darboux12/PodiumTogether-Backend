package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.Discipline;
import com.podium.service.DisciplineService;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Iterable<DisciplineResponseDto>> findAllDiscipline(){

        var disciplines = this.disciplineService.findAllDiscipline();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(disciplines));
    }

    @GetMapping(PodiumEndpoint.findByDisciplineName)
    public ResponseEntity<DisciplineResponseDto> findDisciplineByName(@PathVariable @PodiumValidVariable String discipline){

        var dis = this.disciplineService.findByDisciplineName(discipline);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(dis));
    }

    @PostMapping(PodiumEndpoint.addDiscipline)
    public ResponseEntity addDiscipline(@RequestBody @PodiumValidBody DisciplineRequestDto requestDto) {
        this.disciplineService.addDiscipline(requestDto);
        return ResponseEntity.ok().body("Discipline successfully added");

    }

    @GetMapping(PodiumEndpoint.existDisciplineByName)
    public ResponseEntity<Boolean> existDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        return ResponseEntity.ok().body(this.disciplineService.existByDisciplineName(discipline));
    }

    @DeleteMapping(PodiumEndpoint.deleteDisciplineByName)
    public ResponseEntity deleteDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        this.disciplineService.deleteDisciplineByName(discipline);
        return ResponseEntity.ok().body("Discipline successfully deleted");
    }

    private DisciplineResponseDto convertEntityToResponseDto(Discipline discipline){
        return new DisciplineResponseDto(discipline.getDiscipline());
    }

    private Iterable<DisciplineResponseDto> convertEntityIterableToResponseDto(Iterable<Discipline> disciplines){

        var disciplineResponses = new ArrayList<DisciplineResponseDto>();

        disciplines.forEach(x -> disciplineResponses.add(this.convertEntityToResponseDto(x)));

        return disciplineResponses;
    }

}
