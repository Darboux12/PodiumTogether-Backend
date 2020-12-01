package com.podium.controller.localization;

import com.podium.constant.PodiumEndpoint;
import com.podium.model.dto.request.localization.CityRequestDto;
import com.podium.model.dto.response.localization.CityResponseDto;
import com.podium.service.localization.CityService;
import com.podium.validation.exception.PodiumAlreadyExistException;
import com.podium.validation.exception.PodiumNotExistException;
import com.podium.validation.validators.PodiumValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(PodiumEndpoint.findAllCity)
    public ResponseEntity<Iterable<CityResponseDto>> findAllCity(){
        return ResponseEntity.ok().body(this.cityService.findAllCity());
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity<CityResponseDto> findCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            return ResponseEntity.ok().body(this.cityService.findCityByName(name));

        else throw new PodiumNotExistException("City");
    }

    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody CityRequestDto requestDto){

        PodiumValidator.getInstance().validateRequestBody(requestDto);

        if(this.cityService.existByCityName(requestDto.getCity()))
            throw new PodiumAlreadyExistException("City");

        this.cityService.addCity(requestDto);

        return ResponseEntity.ok().body("City successfully added");

    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity existCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            return ResponseEntity.ok().build();

        else
            throw new PodiumNotExistException("City");
    }

    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable String name){

        if(this.cityService.existByCityName(name))
            this.cityService.deleteCityByName(name);

        else throw new PodiumNotExistException("City");

        return ResponseEntity.ok().body("City successfully deleted");
    }

}
