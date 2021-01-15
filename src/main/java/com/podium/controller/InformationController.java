package com.podium.controller;

import com.podium.controller.dto.other.PodiumCompatibilityEndpoint;
import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.response.ControllerEndpointCompatibilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @PostMapping(PodiumEndpoint.findServerEndpointsCompatibility)
    public ResponseEntity<Iterable<ControllerEndpointCompatibilityResponse>> checkEndpointCompatibility(@RequestBody List<PodiumCompatibilityEndpoint> endpoints){

        var responses = new ArrayList<ControllerEndpointCompatibilityResponse>();
        var serverEndpoints = PodiumEndpoint.getAllEndpoints();

        serverEndpoints.forEach(serverEndpoint -> {

            if(this.containsListEndpointByName(endpoints,serverEndpoint.getName())){

                String serverValue = this.prepareServerEndpointValue(serverEndpoint.getValue());
                String clientValue = this.findEndpointValueByName(endpoints,serverEndpoint.getName());

                if(!serverValue.equals(clientValue))
                    responses.add(this.createValueResponse(serverEndpoint,clientValue,serverValue));

            }

            else {

                String serverValueAgain = this.prepareServerEndpointValue(serverEndpoint.getValue());

                if(!this.containsListEndpointByValue(endpoints,serverValueAgain))
                    responses.add(this.createMissingResponse(serverEndpoint));

                else {

                    String serverName = serverEndpoint.getName();
                    String clientName = this.findEndpointNameByValue(endpoints,serverValueAgain);

                    if(!serverName.equals(clientName))
                        responses.add(this.createNameResponse(serverEndpoint,clientName,serverName));


                }


            }


        });

        return ResponseEntity.ok().body(responses);

    }

    private boolean containsListEndpointByName(List<PodiumCompatibilityEndpoint> endpoints, String name){

        return endpoints
                .stream()
                .map(PodiumCompatibilityEndpoint::getName)
                .collect(Collectors.toList()).contains(name);

    }

    private boolean containsListEndpointByValue(List<PodiumCompatibilityEndpoint> endpoints, String value){

        return endpoints
                .stream()
                .map(PodiumCompatibilityEndpoint::getValue)
                .collect(Collectors.toList()).contains(value);

    }

    private String findEndpointValueByName(List<PodiumCompatibilityEndpoint> endpoints, String name){
        return  Objects.requireNonNull(endpoints.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null)).getValue();


    }

    private String findEndpointNameByValue(List<PodiumCompatibilityEndpoint> endpoints, String value){
        return Objects.requireNonNull(endpoints.stream().filter(x -> x.getValue().equals(value)).findFirst().orElse(null)).getName();


    }

    private ControllerEndpointCompatibilityResponse createValueResponse(PodiumCompatibilityEndpoint endpoint, String clientValue, String serverValue){
        return new ControllerEndpointCompatibilityResponse(endpoint,"Wrong value for name. Actual: " + clientValue + " Excepted: " + serverValue);
    }

    private ControllerEndpointCompatibilityResponse createNameResponse(PodiumCompatibilityEndpoint endpoint, String clientName, String serverName){
        return new ControllerEndpointCompatibilityResponse(endpoint,"Wrong name for value. Actual: " + clientName + " Excepted: " + serverName);
    }

    private ControllerEndpointCompatibilityResponse createMissingResponse(PodiumCompatibilityEndpoint endpoint){
        return new ControllerEndpointCompatibilityResponse(endpoint,"Endpoint is missing or never used");
    }

    private String prepareServerEndpointValue(String serverValue){

        for(int i=0; i<serverValue.length(); i++){

            if(serverValue.charAt(i) == '{')
                return serverValue.substring(0,i);

        }

        return serverValue;

    }

}
