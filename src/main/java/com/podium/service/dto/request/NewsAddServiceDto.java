package com.podium.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class NewsAddServiceDto {
    private String title;
    private String shortText;
    private String linkText;
    private String text;
    private Set<MultipartFile> images;
}
