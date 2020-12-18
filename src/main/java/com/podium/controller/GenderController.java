package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.GenderAddRequest;
import com.podium.controller.dto.response.GenderResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Gender;
import com.podium.service.GenderService;
import com.podium.service.dto.GenderAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
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
    public ResponseEntity<Iterable<GenderResponse>> findAllGender(){

        var genders = this.genderService.findAllGenders();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(genders));
    }

    @GetMapping(PodiumEndpoint.findGenderByName)
    public ResponseEntity<GenderResponse> findGenderByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        var gender = this.genderService.findByGenderName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(gender));


    }

    @PostMapping(PodiumEndpoint.addGender)
    public ResponseEntity addGender(@RequestBody @PodiumValidBody GenderAddRequest requestDto) throws PodiumEntityAlreadyExistException {

        this.genderService.addGender(this.convertAddRequestToServiceDto(requestDto));

        return ResponseEntity.ok().body("Gender successfully added");
    }

    @GetMapping(PodiumEndpoint.existGenderByName)
    public ResponseEntity existGenderByName(@PathVariable @PodiumValidVariable String name){
            return ResponseEntity.ok().body(this.genderService.existGenderByName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteGenderByName)
    public ResponseEntity deleteGenderByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        if(!this.genderService.existGenderByName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Gender not found");

        this.genderService.deleteGenderByName(name);
        return ResponseEntity.ok().body("Gender successfully deleted");
    }

    private GenderAddServiceDto convertAddRequestToServiceDto(GenderAddRequest request){
        return new GenderAddServiceDto(request.getGender());
    }

    private GenderResponse convertEntityToResponseDto(Gender gender){
        return new GenderResponse(gender.getGender());
    }

    private Iterable<GenderResponse> convertEntityIterableToResponseDto(Iterable<Gender> genders){

        var genderResponses = new ArrayList<GenderResponse>();

        genders.forEach(x -> genderResponses.add(this.convertEntityToResponseDto(x)));

        return genderResponses;
    }
}
