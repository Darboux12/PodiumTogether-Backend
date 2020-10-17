package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength
    private String subject;

}
