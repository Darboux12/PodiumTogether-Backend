package com.podium.controller;

import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.service.CityService;
import com.podium.validation.main.PodiumValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city/find/all")
    public ResponseEntity<Iterable<CityResponseDto>> findAllCity(){
        return ResponseEntity.ok().body(this.cityService.findAllCity());
    }

    @GetMapping("/city/find/name/{name}")
    public ResponseEntity<CityResponseDto> findCityByName(@PathVariable String name){
        return ResponseEntity.ok().body(this.cityService.findCityByName(name));
    }

    @PostMapping("/city/add")
    public ResponseEntity addCity(@RequestBody CityRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.cityService.existByCityName(requestDto.getCity()))
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "City already exists");

        this.cityService.addCity(requestDto);
        return ResponseEntity.ok().body("City successfully added");

    }

    @GetMapping("/city/exist/{name}")
    public ResponseEntity existCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/city/delete/{name}")
    public ResponseEntity deleteCityByName(@PathVariable String name){

        if(!this.cityService.existByCityName(name))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "City not found");

        this.cityService.deleteCityByName(name);
        return ResponseEntity.ok().body("City successfully deleted");
    }

}
