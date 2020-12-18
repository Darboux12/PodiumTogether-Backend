package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.CountryAddRequest;
import com.podium.controller.dto.response.CountryResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.Country;
import com.podium.service.CountryService;
import com.podium.service.dto.CountryAddServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
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
    public ResponseEntity<Iterable<CountryResponse>> findAllCountry(){

        var countries = this.countryService.findAllCountry();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(countries));
    }

    @GetMapping(PodiumEndpoint.findCountryByName)
    public ResponseEntity<CountryResponse> findCountryByName(@PathVariable @PodiumValidVariable String name ) throws PodiumEntityNotFoundException {

        var country = this.countryService.findCountryByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(country));
    }

    @PostMapping(PodiumEndpoint.addCountry)
    public ResponseEntity addCountry(@RequestBody @PodiumValidBody CountryAddRequest requestDto) throws PodiumEntityAlreadyExistException {
        this.countryService.addCountry(requestDto);
        return ResponseEntity.ok().body("Country successfully added");
    }

    @GetMapping(PodiumEndpoint.existCountryByName)
    public ResponseEntity<Boolean> existCountryByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.countryService.existCountryByName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCountryByName)
    public ResponseEntity deleteCountryByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {
        this.countryService.deleteCountryByName(name);
        return ResponseEntity.ok().body("Country successfully deleted");
    }

    private CountryAddServiceDto convertAddRequestToServiceDto(CountryAddRequest request ){

        return new CountryAddServiceDto(request.getCountryId(),
                request.getName(),
                request.getPrintableName(),
                request.getIso3(),
                request.getNumCode()
        );
    }

    private CountryResponse convertEntityToResponseDto(Country country){

        return new CountryResponse(
                country.getCountryId(),
                country.getName(),
                country.getPrintableName(),
                country.getIso3(),
                country.getNumCode()
        );

    }

    private Iterable<CountryResponse> convertEntityIterableToResponseDto(Iterable<Country> countries){

        var countryResponses = new ArrayList<CountryResponse>();

        countries.forEach(x -> countryResponses.add(this.convertEntityToResponseDto(x)));

        return countryResponses;
    }


}
