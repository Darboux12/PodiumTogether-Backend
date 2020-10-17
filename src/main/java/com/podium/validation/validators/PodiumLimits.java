package com.podium.validation.validators;

import java.util.List;

public class PodiumLimits {

    public static final int maxUsernameLength = 50;
    public static final int minUsernameLength = 3;

    public static final int maxPasswordLength = 100;
    public static final int minPasswordLength = 8;

    public static final int maxNewsTitleLength = 100;
    public static final int minNewsTitleLength = 8;

    public static final int maxNewsShortTextLength = 100;
    public static final int minNewsShortTextLength = 8;

    public static final int maxNewsLinkTextLength = 100;
    public static final int minNewsLinkTextLength = 8;

    public static final int maxNewsFullTextLength = 100;
    public static final int minNewsFullTextLength = 8;

    public static final int maxEmailLength = 100;

    public static final List<String> acceptedImagesTypes = List.of(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/tiff",
            "image/gif",
            "image/raw"

    );

    public static final int minContactMessageLength = 10;
    public static final int maxContactMessageLength = 300;

    public static final int minSubjectLength = 3;
    public static final int maxSubjectLength = 30;


}
