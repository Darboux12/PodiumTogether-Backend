package com.podium.service.dto.request;

import com.podium.service.dto.other.LocalizationServiceDto;
import com.podium.service.dto.other.BusinessDayServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PlaceAddServiceRequest {
    private String name;
    private String discipline;
    private LocalizationServiceDto localizationDto;
    private Set<BusinessDayServiceDto> businessDayDtos;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private String author;
    private Set<MultipartFile> images;
    private Set<MultipartFile> documents;
}
