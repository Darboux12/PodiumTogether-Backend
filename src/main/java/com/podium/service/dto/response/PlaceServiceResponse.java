package com.podium.service.dto.response;

import com.podium.service.dto.other.BusinessDayServiceDto;
import com.podium.service.dto.other.FileServiceDto;
import com.podium.service.dto.other.LocalizationServiceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceServiceResponse {
    private int id;
    private String name;
    private String discipline;
    private LocalizationServiceDto localizationDto;
    private List<BusinessDayServiceDto> businessDayDtos;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private List<FileServiceDto> images;
    private Set<ReviewServiceResponse> reviews;
}
