package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.CountryAddControllerRequest;
import com.podium.controller.dto.response.CountryControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Country;
import com.podium.service.CountryService;
import com.podium.service.dto.request.CountryAddServiceRequest;
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
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(PodiumEndpoint.findAllCountry)
    public ResponseEntity<Iterable<CountryControllerResponse>> findAllCountry(){
        var countries = this.countryService.findAllCountry();
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertCountryEntityIterableToResponseDto(countries));
    }

    @GetMapping(PodiumEndpoint.findCountryByName)
    public ResponseEntity<CountryControllerResponse> findCountryByName(@PathVariable @PodiumValidVariable String name ) throws PodiumEntityNotFoundException {
        var country = this.countryService.findCountryByName(name);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertCountryEntityToResponseDto(country));
    }

    @GetMapping(PodiumEndpoint.existCountryByName)
    public ResponseEntity<Boolean> existCountryByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.countryService.existCountryByName(name));
    }

}
