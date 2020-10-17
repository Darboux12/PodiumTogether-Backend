package com.podium.model.dto.request;

import com.podium.validation.annotation.PodiumLength;
import com.podium.validation.annotation.PodiumTextNotEmpty;
import com.podium.validation.validators.PodiumLimits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsRequestDto {

    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minNewsTitleLength, max = PodiumLimits.maxNewsTitleLength)
    private String title;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minNewsShortTextLength, max = PodiumLimits.maxNewsShortTextLength)
    private String shortText;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minNewsLinkTextLength, max = PodiumLimits.maxNewsLinkTextLength)
    private String linkText;
    @PodiumTextNotEmpty
    @PodiumLength(min = PodiumLimits.minNewsFullTextLength, max = PodiumLimits.maxNewsFullTextLength)
    private String text;

}
