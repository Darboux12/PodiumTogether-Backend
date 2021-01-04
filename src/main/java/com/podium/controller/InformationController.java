package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.response.GenderControllerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InformationController {

    @GetMapping(PodiumEndpoint.findServerAddress)
    public ResponseEntity findServerAddress(){
        return ResponseEntity.ok().body(PodiumPath.server);
    }

    @GetMapping(PodiumEndpoint.findServerEndpoints)
    public ResponseEntity findServerEndpoints(){
        return ResponseEntity.ok().body(PodiumEndpoint.getAllEndpoints());
    }




}
