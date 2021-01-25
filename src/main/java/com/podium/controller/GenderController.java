package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.GenderAddControllerRequest;
import com.podium.controller.dto.response.GenderControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Gender;
import com.podium.service.GenderService;
import com.podium.service.dto.request.GenderAddServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Iterable<GenderControllerResponse>> findAllGender(){
        var genders = this.genderService.findAllGenders();
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertGenderEntityIterableToResponseDto(genders));
    }

    @GetMapping(PodiumEndpoint.findGenderByName)
    public ResponseEntity<GenderControllerResponse> findGenderByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {
        var gender = this.genderService.findByGenderName(name);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertGenderEntityToResponseDto(gender));
    }

    // ADMIN
    @PostMapping(PodiumEndpoint.addGender)
    public ResponseEntity addGender(@RequestBody @PodiumValidBody GenderAddControllerRequest requestDto, Authentication authentication) throws PodiumEntityAlreadyExistException, PodiumAuthorityException, PodiumEntityNotFoundException {
        this.genderService.addGender(ControllerRequestConverter.getInstance().convertGenderAddRequestToServiceDto(requestDto),authentication.getName());
        return ResponseEntity.ok().body("Gender successfully added");
    }

    @GetMapping(PodiumEndpoint.existGenderByName)
    public ResponseEntity<Boolean> existGenderByName(@PathVariable @PodiumValidVariable String name){
            return ResponseEntity.ok().body(this.genderService.existGenderByName(name));
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteGenderByName)
    public ResponseEntity deleteGenderByName(@PathVariable @PodiumValidVariable String name, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        if(!this.genderService.existGenderByName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Gender not found");

        this.genderService.deleteGenderByName(name,authentication.getName());
        return ResponseEntity.ok().body("Gender successfully deleted");
    }




}
