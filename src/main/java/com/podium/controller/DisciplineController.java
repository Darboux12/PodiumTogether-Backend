package com.podium.controller;


import com.podium.service.DisciplineService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DisciplineController {

    private DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping("/discipline/find/all")
    public ResponseEntity findAllDiscipline(){
        return ResponseEntity.ok().body(this.disciplineService.findAllDiscipline());
    }

    @PostMapping("/discipline/add")
    public ResponseEntity addDiscipline(@RequestParam("discipline") String discipline){

        if(discipline.isEmpty()){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Discipline");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Discipline cannot be empty");

        }

        if(this.disciplineService.existByDisciplineName(discipline)){

            HttpHeaders headers = new HttpHeaders();
            headers.add("Already-Exist","Discipline");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Discipline already exist");
        }

        this.disciplineService.addDiscipline(discipline);
        return ResponseEntity.ok().body("Discipline successfully added");

    }

    @GetMapping("/discipline/exist/{discipline}")
    public ResponseEntity existUserByUsername(@PathVariable String discipline){

        if(this.disciplineService.existByDisciplineName(discipline))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.badRequest().build();
    }
}
