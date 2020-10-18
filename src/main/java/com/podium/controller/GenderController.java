package com.podium.controller;

import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.service.GenderService;
import com.podium.validation.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GenderController {

    private GenderService genderService;

    @Autowired
    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping("/gender/find/all")
    public ResponseEntity<Iterable<GenderResponseDto>> findAllGender(){
        return ResponseEntity.ok().body(this.genderService.findAllGenders());
    }

    @GetMapping("/gender/find/name/{name}")
    public ResponseEntity<GenderResponseDto> findGenderByName(@PathVariable String name){
        return ResponseEntity.ok().body(this.genderService.findByGenderName(name));
    }

    @PostMapping("/gender/add")
    public ResponseEntity addGender(@RequestBody GenderRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.genderService.existByGenderName(requestDto.getGender()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Country already exists");

        this.genderService.addGender(requestDto);
        return ResponseEntity.ok().body("Country successfully added");

    }

    @GetMapping("/gender/exist/{name}")
    public ResponseEntity existGenderByName(@PathVariable String name){

        if(this.genderService.existByGenderName(name))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/gender/delete/{name}")
    public ResponseEntity deleteGenderByName(@PathVariable String name){

        if(!this.genderService.existByGenderName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country not found");

        this.genderService.deleteGenderByName(name);
        return ResponseEntity.ok().body("Country successfully deleted");
    }

}
