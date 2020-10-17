package com.podium.controller;


import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.DisciplineResponseDto;
import com.podium.service.DisciplineService;
import com.podium.validation.PodiumValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DisciplineController {

    private DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping("/discipline/find/all")
    public ResponseEntity<Iterable<DisciplineResponseDto>> findAllDiscipline(){
        return ResponseEntity.ok().body(this.disciplineService.findAllDiscipline());
    }

    @PostMapping("/discipline/add")
    public ResponseEntity addDiscipline(@RequestBody DisciplineRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.disciplineService.existByDisciplineName(requestDto.getDiscipline()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Discipline already exists");

        this.disciplineService.addDiscipline(requestDto);
        return ResponseEntity.ok().body("Discipline successfully added");

    }

    @GetMapping("/discipline/exist/{discipline}")
    public ResponseEntity existDisciplineByName(@PathVariable String discipline){

        if(this.disciplineService.existByDisciplineName(discipline))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/discipline/delete/{discipline}")
    public ResponseEntity deleteDisciplineByName(@PathVariable String discipline){

        if(!this.disciplineService.existByDisciplineName(discipline))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Discipline not found");

        this.disciplineService.deleteDisciplineByName(discipline);
        return ResponseEntity.ok().body("Discipline successfully deleted");
    }

}
