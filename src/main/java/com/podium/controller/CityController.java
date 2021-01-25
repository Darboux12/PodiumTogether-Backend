package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.converter.ControllerResponseConverter;
import com.podium.controller.dto.request.CityAddControllerRequest;
import com.podium.controller.dto.response.CityControllerResponse;
import com.podium.controller.validation.validator.annotation.PodiumValidBody;
import com.podium.controller.validation.validator.annotation.PodiumValidVariable;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.CityService;
import com.podium.service.exception.PodiumAuthorityException;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertCityEntityIterableToResponseDto(cities));
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity<CityControllerResponse> findCityByName(@PathVariable @PodiumValidVariable String name) throws PodiumEntityNotFoundException {
        var city = this.cityService.findCityByName(name);
        return ResponseEntity.ok().body(ControllerResponseConverter.getInstance().convertCityEntityToResponseDto(city));
    }

    // ADMIN
    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody @PodiumValidBody CityAddControllerRequest requestDto, Authentication authentication) throws PodiumEntityAlreadyExistException, PodiumAuthorityException, PodiumEntityNotFoundException {
        this.cityService.addCity(ControllerRequestConverter.getInstance().convertCityAddRequestToServiceDto(requestDto),authentication.getName());
        return ResponseEntity.ok().body("City successfully added");
    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity<Boolean> existCityByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.cityService.existCityByName(name));
    }

    // ADMIN
    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String name,Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException {
        this.cityService.deleteCityByName(ControllerRequestConverter.getInstance().convertCityDeleteRequestToServiceDto(name),authentication.getName());
        return ResponseEntity.ok().body("City successfully deleted");
    }

}
