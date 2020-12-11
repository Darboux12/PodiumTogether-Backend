package com.podium.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewsAddServiceDto {
    private String title;
    private String shortText;
    private String linkText;
    private String text;
}
