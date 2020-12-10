package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.model.entity.City;
import com.podium.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class CityController{

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(PodiumEndpoint.findAllCity)
    public ResponseEntity<Iterable<CityResponseDto>> findAllCity(){

        var cities = this.cityService.findAllCity();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(cities));
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity<CityResponseDto> findCityByName(@PathVariable @PodiumValidVariable String name){

        var city = this.cityService.findCityByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(city));
    }

    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody @PodiumValidBody CityRequestDto requestDto){

        this.cityService.addCity(requestDto);

        return ResponseEntity.ok().body("City successfully added");
    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity<Boolean> existCityByName(@PathVariable @PodiumValidVariable String name){

        return ResponseEntity.ok().body(this.cityService.existByCityName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String name){

        this.cityService.deleteCityByName(name);

        return ResponseEntity.ok().body("City successfully deleted");
    }

    private CityResponseDto convertEntityToResponseDto(City city){
        return new CityResponseDto(city.getCity());
    }

    private Iterable<CityResponseDto> convertEntityIterableToResponseDto(Iterable<City> cities){

        var cityResponses = new ArrayList<CityResponseDto>();

        cities.forEach(x -> cityResponses.add(this.convertEntityToResponseDto(x)));

        return cityResponses;
    }

}
