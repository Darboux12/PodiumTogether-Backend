package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.localization.CityRequestDto;
import com.podium.model.dto.response.localization.CityResponseDto;
import com.podium.service.CityService;
import com.podium.controller.response.PodiumAlreadyExistWebException;
import com.podium.controller.response.PodiumNotExistWebException;
import com.podium.model.dto.validation.validator.PodiumDtoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CityController {

    private CityService cityService;
    private PodiumDtoValidator dtoValidator;

    public CityController(CityService cityService, PodiumDtoValidator dtoValidator) {
        this.cityService = cityService;
        this.dtoValidator = dtoValidator;
    }

    @GetMapping(PodiumEndpoint.findAllCity)
    public ResponseEntity<Iterable<CityResponseDto>> findAllCity(){
        return ResponseEntity.ok().body(this.cityService.findAllCity());
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity<CityResponseDto> findCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            return ResponseEntity.ok().body(this.cityService.findCityByName(name));

        else throw new PodiumNotExistWebException("City");
    }

    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody CityRequestDto requestDto){

        if(this.cityService.existByCityName(requestDto.getCity()))
            throw new PodiumAlreadyExistWebException("City");

        this.cityService.addCity(requestDto);

        return ResponseEntity.ok().body("City successfully added");

    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity existCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            return ResponseEntity.ok().build();

        else
            throw new PodiumNotExistWebException("City");
    }

    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            this.cityService.deleteCityByName(name);

        else throw new PodiumNotExistWebException("City");

        return ResponseEntity.ok().body("City successfully deleted");
    }

}
