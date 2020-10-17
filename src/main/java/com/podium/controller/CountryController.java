package com.podium.controller;

import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.request.DisciplineRequestDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.service.CountryService;
import com.podium.validation.PodiumValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CountryController {

    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/country/find/all")
       public ResponseEntity<Iterable<CountryResponseDto>> findAllCountry(){
        return ResponseEntity.ok().body(this.countryService.findAllCountry());
    }

    @PostMapping("/country/add")
    public ResponseEntity addCountry(@RequestBody CountryRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.countryService.existCountryByName(requestDto.getName()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Country already exists");

        this.countryService.addCountry(requestDto);
        return ResponseEntity.ok().body("Country successfully added");

    }

    @GetMapping("/country/exist/{name}")
    public ResponseEntity existDisciplineByName(@PathVariable String name){

        if(this.countryService.existCountryByName(name))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/country/delete/{name}")
    public ResponseEntity deleteDisciplineByName(@PathVariable String name){

        if(!this.countryService.existCountryByName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country not found");

        this.countryService.deleteCountryByName(name);
        return ResponseEntity.ok().body("Country successfully deleted");
    }







}
