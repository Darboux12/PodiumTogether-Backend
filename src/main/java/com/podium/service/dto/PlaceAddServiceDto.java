package com.podium.service.dto;

import com.podium.controller.dto.other.BusinessDayDto;
import com.podium.controller.dto.other.LocalizationDto;
import com.podium.controller.dto.other.RatingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
    private ReviewServiceDto reviewServiceDto;
}
