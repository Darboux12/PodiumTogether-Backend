package com.podium.model.dto.request;

import com.podium.model.dto.validation.annotation.dto.PodiumLength;
import com.podium.model.dto.validation.annotation.dto.PodiumTextNotEmpty;
import com.podium.constant.PodiumLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
