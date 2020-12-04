package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.service.DisciplineService;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return ResponseEntity.ok().body(this.disciplineService.findAllDiscipline());
    }

    @GetMapping(PodiumEndpoint.findByDisciplineName)
    public ResponseEntity findDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        return ResponseEntity.ok().body(this.disciplineService.findByDisciplineName(discipline));
    }

    @PostMapping(PodiumEndpoint.addDiscipline)
    public ResponseEntity addDiscipline(@RequestBody @PodiumValidBody DisciplineRequestDto requestDto) {
        this.disciplineService.addDiscipline(requestDto);
        return ResponseEntity.ok().body("Discipline successfully added");

    }

    @GetMapping(PodiumEndpoint.existDisciplineByName)
    public ResponseEntity existDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        return ResponseEntity.ok().body(this.disciplineService.existByDisciplineName(discipline));
    }

    @DeleteMapping(PodiumEndpoint.deleteDisciplineByName)
    public ResponseEntity deleteDisciplineByName(@PathVariable @PodiumValidVariable String discipline){
        this.disciplineService.deleteDisciplineByName(discipline);
        return ResponseEntity.ok().body("Discipline successfully deleted");
    }

}
