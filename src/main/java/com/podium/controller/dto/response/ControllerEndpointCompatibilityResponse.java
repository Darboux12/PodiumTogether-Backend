package com.podium.controller.dto.response;

import com.podium.controller.dto.other.PodiumCompatibilityEndpoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ControllerEndpointCompatibilityResponse {

    private PodiumCompatibilityEndpoint endpoint;
    private String message;
}
