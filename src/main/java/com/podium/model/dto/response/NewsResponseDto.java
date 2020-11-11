package com.podium.model.dto.response;

import com.podium.model.other.PodiumFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDto {

    private int id;
    private String title;
    private String shortText;
    private String text;
    private String linkText;
    private Date date;
    private List<PodiumFile> podiumFiles;

}
