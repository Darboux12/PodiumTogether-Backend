package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContactAddServiceRequest {
    private String userEmail;
    private String subject;
    private String message;
}
