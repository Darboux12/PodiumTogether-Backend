package com.podium.service.dto.response;

import com.podium.controller.dto.other.FileControllerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsServiceResponse {

    private int id;
    private String title;
    private String shortText;
    private String text;
    private String linkText;
    private Date date;
    private List<FileControllerDto> images;

}
