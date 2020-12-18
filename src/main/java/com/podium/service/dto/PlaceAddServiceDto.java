package com.podium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PlaceAddServiceDto {
    private String name;
    private String discipline;
    private LocalizationServiceDto localizationDto;
    private Set<BusinessDayServiceDto> businessDayDtos;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private Set<MultipartFile> images;
    private Set<MultipartFile> documents;
}
