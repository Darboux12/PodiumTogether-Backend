package com.podium.model.response;

import com.podium.model.entity.News;
import com.podium.model.entity.PodiumResource;
import com.podium.model.other.PodiumFile;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsResponse {

    private News news;
    private List<PodiumFile> podiumFiles = new ArrayList<>();


}
