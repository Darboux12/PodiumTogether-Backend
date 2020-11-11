package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.service.CountryService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ResponseEntity addCountry(@RequestBody CountryRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.countryService.existCountryByName(requestDto.getName()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Country already exists");

        this.countryService.addCountry(requestDto);
        return ResponseEntity.ok().body("Country successfully added");

    }

    @GetMapping(PodiumEndpoint.existCountryByName)
    public ResponseEntity existCountryByName(@PathVariable String name){

        if(this.countryService.existCountryByName(name))
            return ResponseEntity.ok().build();

        else throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Country with given name does not exist");
    }

    @DeleteMapping(PodiumEndpoint.deleteCountryByName)
    public ResponseEntity deleteCountryByName(@PathVariable String name){

        if(!this.countryService.existCountryByName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country not found");

        this.countryService.deleteCountryByName(name);
        return ResponseEntity.ok().body("Country successfully deleted");
    }

}
