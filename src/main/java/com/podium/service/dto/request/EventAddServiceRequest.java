package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class EventAddServiceRequest {
    private String title;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
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
