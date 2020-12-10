package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.GenderRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.response.GenderResponseDto;
import com.podium.model.dto.validation.exception.PodiumEmptyTextException;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.City;
import com.podium.model.entity.Gender;
import com.podium.service.GenderService;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class GenderController {

    private GenderService genderService;

    public GenderController(GenderService genderService) {
        this.genderService = genderService;
    }

    @GetMapping(PodiumEndpoint.findAllGender)
    public ResponseEntity<Iterable<GenderResponseDto>> findAllGender(){

        var genders = this.genderService.findAllGenders();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(genders));
    }

    @GetMapping(PodiumEndpoint.findGenderByName)
    public ResponseEntity<GenderResponseDto> findGenderByName(@PathVariable @PodiumValidVariable String name){

        var gender = this.genderService.findByGenderName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(gender));


    }

    @PostMapping(PodiumEndpoint.addGender)
    public ResponseEntity addGender(@RequestBody @PodiumValidBody GenderRequestDto requestDto){

        if(this.genderService.existByGenderName(requestDto.getGender()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Gender already exists");

        this.genderService.addGender(requestDto);
        return ResponseEntity.ok().body("Gender successfully added");

    }

    @GetMapping(PodiumEndpoint.existGenderByName)
    public ResponseEntity existGenderByName(@PathVariable @PodiumValidVariable String name){
            return ResponseEntity.ok().body(this.genderService.existByGenderName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteGenderByName)
    public ResponseEntity deleteGenderByName(@PathVariable @PodiumValidVariable String name){

        if(!this.genderService.existByGenderName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Gender not found");

        this.genderService.deleteGenderByName(name);
        return ResponseEntity.ok().body("Gender successfully deleted");
    }

    private GenderResponseDto convertEntityToResponseDto(Gender gender){
        return new GenderResponseDto(gender.getGender());
    }

    private Iterable<GenderResponseDto> convertEntityIterableToResponseDto(Iterable<Gender> genders){

        var genderResponses = new ArrayList<GenderResponseDto>();

        genders.forEach(x -> genderResponses.add(this.convertEntityToResponseDto(x)));

        return genderResponses;
    }
}
