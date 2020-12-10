package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.CountryRequestDto;
import com.podium.model.dto.response.ContactResponseDto;
import com.podium.model.dto.response.CountryResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Country;
import com.podium.service.CountryService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Iterable<CountryResponseDto>> findAllCountry(){

        var countries = this.countryService.findAllCountry();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(countries));
    }

    @GetMapping(PodiumEndpoint.findCountryByName)
    public ResponseEntity<CountryResponseDto> findCountryByName(@PathVariable @PodiumValidVariable String name ){

        var country = this.countryService.findCountryByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(country));
    }

    @PostMapping(PodiumEndpoint.addCountry)
    public ResponseEntity addCountry(@RequestBody @PodiumValidBody CountryRequestDto requestDto){
        this.countryService.addCountry(requestDto);
        return ResponseEntity.ok().body("Country successfully added");
    }

    @GetMapping(PodiumEndpoint.existCountryByName)
    public ResponseEntity<Boolean> existCountryByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.countryService.existCountryByName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCountryByName)
    public ResponseEntity deleteCountryByName(@PathVariable @PodiumValidVariable String name){
        this.countryService.deleteCountryByName(name);
        return ResponseEntity.ok().body("Country successfully deleted");
    }

    private CountryResponseDto convertEntityToResponseDto(Country country){

        return new CountryResponseDto(
                country.getCountryId(),
                country.getName(),
                country.getPrintableName(),
                country.getIso3(),
                country.getNumCode()
        );

    }

    private Iterable<CountryResponseDto> convertEntityIterableToResponseDto(Iterable<Country> countries){

        var countryResponses = new ArrayList<CountryResponseDto>();

        countries.forEach(x -> countryResponses.add(this.convertEntityToResponseDto(x)));

        return countryResponses;
    }


}
