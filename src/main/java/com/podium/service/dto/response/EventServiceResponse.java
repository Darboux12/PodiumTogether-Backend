package com.podium.service.dto.response;

import com.podium.controller.dto.other.FileControllerDto;
import com.podium.service.dto.other.FileServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventServiceResponse {
    private int id;
    private String title;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private int peopleNumber;
    private int minAge;
    private int maxAge;
    private String description;
    private Set<String> usersJoined;
    private String author;
    private String discipline;
    private int views;
    private Set<FileServiceDto> images;
    private Set<FileServiceDto> documents;
    private LocalDateTime creationDate;
    private PlaceServiceResponse place;
}
