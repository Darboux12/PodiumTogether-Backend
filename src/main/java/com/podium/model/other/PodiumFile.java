package com.podium.model.other;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PodiumFile {

    private String name;
    private String type;
    private byte[] content;

}
