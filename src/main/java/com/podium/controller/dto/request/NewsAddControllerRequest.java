package com.podium.controller.dto.request;

import com.podium.controller.validation.annotation.PodiumLength;
import com.podium.controller.validation.annotation.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsAddControllerRequest {

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
