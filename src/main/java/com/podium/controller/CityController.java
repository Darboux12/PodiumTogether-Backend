package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.link.PodiumLinkable;
import com.podium.model.dto.request.CityRequestDto;
import com.podium.model.dto.response.CityResponseDto;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidBody;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidVariable;
import com.podium.model.dto.validation.validator.annotation.validator.PodiumValidateController;
import com.podium.service.CityService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class CityController implements PodiumLinkable<CityResponseDto> {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(PodiumEndpoint.findAllCity)
    public ResponseEntity<Iterable<CityResponseDto>> findAllCity(){
        var responseDtos = this.cityService.findAllCity();
        return ResponseEntity
                .ok().body(this.addResourceCollectionLink(responseDtos));
    }

    @GetMapping(PodiumEndpoint.findCityByName)
    public ResponseEntity findCityByName(@PathVariable @PodiumValidVariable String name){
        CityResponseDto responseDto = this.cityService.findCityByName(name);
        return ResponseEntity.ok().body(this.addResourceLink(responseDto));
    }

    @PostMapping(PodiumEndpoint.addCity)
    public ResponseEntity addCity(@RequestBody @PodiumValidBody CityRequestDto requestDto){
        this.cityService.addCity(requestDto);
        return ResponseEntity.ok().body("City successfully added");
    }

    @GetMapping(PodiumEndpoint.existCityByName)
    public ResponseEntity existCityByName(@PathVariable @PodiumValidVariable String name){
        return ResponseEntity.ok().body(this.cityService.existByCityName(name));
    }

    @DeleteMapping(PodiumEndpoint.deleteCityByName)
    public ResponseEntity deleteCityByName(@PathVariable @PodiumValidVariable String name){
        this.cityService.deleteCityByName(name);
        return ResponseEntity.ok().body("City successfully deleted");
    }

    @Override
    public CityResponseDto addResourceLink(CityResponseDto responseDto) {
        responseDto.add(linkTo(methodOn(CityController.class).findCityByName(responseDto.getCity())).withSelfRel());
        responseDto.add(linkTo(methodOn(CityController.class)
                .findAllCity()).withRel("All cities"));

        return responseDto;
    }

    @Override
    public CollectionModel<CityResponseDto> addResourceCollectionLink(Iterable<CityResponseDto> collection) {
        collection
                .forEach(res -> res.add(linkTo(methodOn(CityController.class)
                        .findCityByName(res.getCity()))
                        .withSelfRel()
                ));

        Link collectionLink = linkTo(methodOn(CityController.class)
                .findAllCity()).withRel("All cities");

        return CollectionModel.of(collection,collectionLink);
    }
}
