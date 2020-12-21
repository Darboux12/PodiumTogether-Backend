package com.podium.controller.dto.response;

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
    private LocalizationControllerDto localizationControllerDto;
    private List<BusinessDayControllerDto> businessDayControllerDtos;
    private double cost;
    private double usageTime;
    private int minAge;
    private int maxAge;
    private List<FileControllerDto> images;
    Set<ReviewControllerResponse> reviews;
}
