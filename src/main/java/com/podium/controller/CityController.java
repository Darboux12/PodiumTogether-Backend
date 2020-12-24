package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.CityAddControllerRequest;
import com.podium.controller.dto.response.CityControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.dal.entity.City;
import com.podium.service.CityService;
import com.podium.service.dto.request.CityAddServiceRequest;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
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
    public ResponseEntity<Iterable<CityControllerResponse>> findAllCity(){

        var cities = this.cityService.findAllCity();

        return ResponseEntity.ok().body(this.convertEntityIterableToResponseDto(cities));
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity<CityControllerResponse> findCityByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        var city = this.cityService.findCityByName(name);

        return ResponseEntity.ok().body(this.convertEntityToResponseDto(city));
    }

    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody @PodiumValidBody CityAddControllerRequest requestDto) throws PodiumEntityAlreadyExistException {

        this.cityService.addCity(this.convertAddRequestToServiceDto(requestDto));

        return ResponseEntity.ok().body("City successfully added");
    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity<Boolean> existCityByName(@PathVariable @PodiumValidVariable String name){

        return ResponseEntity.ok().body(this.cityService.existCityByName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {

        this.cityService.deleteCityByName(name);

        return ResponseEntity.ok().body("City successfully deleted");
    }

    private CityAddServiceRequest convertAddRequestToServiceDto(CityAddControllerRequest request){
        return new CityAddServiceRequest(request.getCity());
    }

    private CityControllerResponse convertEntityToResponseDto(City city){
        return new CityControllerResponse(city.getCity());
    }

    private Iterable<CityControllerResponse> convertEntityIterableToResponseDto(Iterable<City> cities){

        var cityResponses = new ArrayList<CityControllerResponse>();

        cities.forEach(x -> cityResponses.add(this.convertEntityToResponseDto(x)));

        return cityResponses;
    }

}
