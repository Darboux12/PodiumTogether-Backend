package com.podium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventAddServiceDto {
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private String city;
    private int number;
    private String street;
    private String postal;
    private String discipline;
    private int people;
    private List<String> genders;
    private int minAge;
    private int maxAge;
    private double cost;
    private String description;
    private String author;
}
