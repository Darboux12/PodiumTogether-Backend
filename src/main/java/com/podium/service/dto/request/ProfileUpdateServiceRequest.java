package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ProfileUpdateServiceRequest {
    private int id;
    private String username;
    private String email;
    private String password;
    private String country;
    private Date birthday;
    private String description;
    private MultipartFile image;
}
