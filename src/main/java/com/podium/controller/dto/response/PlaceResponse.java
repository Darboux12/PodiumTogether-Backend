package com.podium.controller.dto.response;

import com.podium.controller.dto.other.BusinessDayDto;
import com.podium.controller.dto.other.LocalizationDto;
import com.podium.controller.dto.other.PodiumFileDto;
import com.podium.controller.dto.other.ReviewResponse;
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
public class PlaceResponse {
    private int id;
    private String name;
    private String discipline;
    private LocalizationDto localizationDto;
    private List<BusinessDayDto> businessDayDtos;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private List<PodiumFileDto> images;
    Set<ReviewResponse> reviews;

}
