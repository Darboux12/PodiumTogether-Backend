package com.podium.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactResponseDto {
    private int id;
    private String email;
    private String message;
    private String subject;
}
