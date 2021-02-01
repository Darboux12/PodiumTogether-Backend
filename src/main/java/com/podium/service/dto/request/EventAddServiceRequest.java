package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EventAddServiceRequest {
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private int people;
    private Set<String> genders;
    private int minAge;
    private int maxAge;
    private String description;
    private String author;
    private String placeName;
    private Set<MultipartFile> images;
    private Set<MultipartFile> documents;
}
