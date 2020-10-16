package com.podium.model.response;

import com.podium.model.entity.News;
import com.podium.model.entity.PodiumResource;
import com.podium.model.other.PodiumFile;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NewsResponse {

    private int id;
    private String title;
    private String shortText;
    private String text;
    private String linkText;
    private Date date;
    private List<PodiumFile> podiumFiles = new ArrayList<>();

}
