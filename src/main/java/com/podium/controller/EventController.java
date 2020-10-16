package com.podium.controller;

import com.podium.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/event/add")
    public ResponseEntity addEvent(
            @RequestParam("title") String title,
            @RequestParam("date") String date,
            @RequestParam("city") String city,
            @RequestParam("number") int number,
            @RequestParam("street") String street,
            @RequestParam("postal") String postal,
            @RequestParam("discipline") String discipline,
            @RequestParam("people") int people,
            @RequestParam("genders")List<String> genders,
            @RequestParam("minAge") int minAge,
            @RequestParam("maxAge") int maxAge,
            @RequestParam("cost") double cost,
            @RequestParam("time") double time,
            @RequestParam("description") String description,
            @RequestParam("startHour") String startHour,
            @RequestParam("endHour") String endHour,
            @RequestParam("documents") List<MultipartFile> documents,
            @RequestParam("images") List<MultipartFile> images
    ){

        /*

        if(PodiumValidator.isTextEmpty(title))
            return PodiumValidationResponse.EmptyValue("Title");

        if(PodiumValidator.isTextEmpty(date))
            return PodiumValidationResponse.EmptyValue("Event-Date");

        if(!PodiumValidator.isValidDate(date))
            return PodiumValidationResponse.InvalidDate("Event");

        if(PodiumValidator.isTextEmpty(city))
            return PodiumValidationResponse.EmptyValue("City");

        if(PodiumValidator.isIntNumberEmpty(number))
            return PodiumValidationResponse.EmptyValue("Number");

        if(PodiumValidator.isTextEmpty(street))
            return PodiumValidationResponse.EmptyValue("Street");

        if(PodiumValidator.isTextEmpty(postal))
            return PodiumValidationResponse.EmptyValue("Postal-Code");

        if(PodiumValidator.isTextEmpty(discipline))
            return PodiumValidationResponse.EmptyValue("Discipline");

        if(PodiumValidator.isIntNumberEmpty(people))
            return PodiumValidationResponse.EmptyValue("People-Number"); */






        if(genders.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Gender");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Gender cannot be empty");
        }

        if(minAge == 0){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Min-Age");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Minimal age cannot be empty");
        }

        if(maxAge == 0){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Max-Age");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Maximal age cannot be empty");
        }

        if(time == 0){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Time");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Time cannot be empty");
        }

        if(description.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Description");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Description cannot be empty");
        }

        if(startHour.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","Start-Hour");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("Start hour cannot be empty");
        }

        if(endHour.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Empty-Value","End-Hour");
            return ResponseEntity
                    .status(409)
                    .headers(headers)
                    .body("End hour cannot be empty");
        }

        return ResponseEntity
                .ok().build();


    }

}
