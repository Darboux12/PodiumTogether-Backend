package com.podium.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.podium.controller.dto.other.BusinessDayControllerDto;
import com.podium.controller.dto.other.LocalizationControllerDto;
import com.podium.controller.dto.other.FileControllerDto;
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
public class PlaceControllerResponse {
    private int id;
    private String name;
    private String discipline;
    @JsonProperty("localization")
    private LocalizationControllerDto localizationControllerDto;
    @JsonProperty("businessDays")
    private Set<BusinessDayControllerDto> businessDayControllerDtos;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private String author;
    private Set<FileControllerDto> images;
    private Set<FileControllerDto> documents;
    Set<ReviewControllerResponse> reviews;
}
