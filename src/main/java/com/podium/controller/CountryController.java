package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(PodiumEndpoint.findAllCountry)
    public ResponseEntity<Iterable<CountryResponseDto>> findAllCountry(){
        return ResponseEntity.ok().body(this.countryService.findAllCountry());
    }

    @PostMapping(PodiumEndpoint.addCountry)
    public ResponseEntity addCountry(@RequestBody @PodiumValidBody CountryRequestDto requestDto){
        this.countryService.addCountry(requestDto);
        return ResponseEntity.ok().body("Country successfully added");
    }

    @GetMapping(PodiumEndpoint.existCountryByName)
    public ResponseEntity existCountryByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.countryService.existCountryByName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCountryByName)
    public ResponseEntity deleteCountryByName(@PathVariable @PodiumValidVariable String name){
        this.countryService.deleteCountryByName(name);
        return ResponseEntity.ok().body("Country successfully deleted");
    }

}
