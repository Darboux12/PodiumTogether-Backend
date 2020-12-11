package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.CityAddRequest;
import com.podium.controller.dto.response.CityResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.City;
import com.podium.service.CityService;
import com.podium.service.dto.CityAddServiceDto;
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
    public ResponseEntity<Iterable<CityResponse>> findAllCity(){

        var cities = this.cityService.findAllCity();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(cities));
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity<CityResponse> findCityByName(@PathVariable @PodiumValidVariable String name){

        var city = this.cityService.findCityByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(city));
    }

    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody @PodiumValidBody CityAddRequest requestDto){

        this.cityService.addCity(this.convertAddRequestToServiceDto(requestDto));

        return ResponseEntity.ok().body("City successfully added");
    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity<Boolean> existCityByName(@PathVariable @PodiumValidVariable String name){

        return ResponseEntity.ok().body(this.cityService.existCityByName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String name){

        this.cityService.deleteCityByName(name);

        return ResponseEntity.ok().body("City successfully deleted");
    }

    private CityAddServiceDto convertAddRequestToServiceDto(CityAddRequest request){
        return new CityAddServiceDto(request.getCity());
    }

    private CityResponse convertEntityToResponseDto(City city){
        return new CityResponse(city.getCity());
    }

    private Iterable<CityResponse> convertEntityIterableToResponseDto(Iterable<City> cities){

        var cityResponses = new ArrayList<CityResponse>();

        cities.forEach(x -> cityResponses.add(this.convertEntityToResponseDto(x)));

        return cityResponses;
    }

}
