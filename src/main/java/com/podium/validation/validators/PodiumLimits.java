package com.podium.validation.validators;

import java.util.List;

public class PodiumLimits {

    /** USERNAME */
    public static final int maxUsernameLength = 50;
    public static final int minUsernameLength = 3;

    /** PASSWORD*/
    public static final int maxPasswordLength = 100;
    public static final int minPasswordLength = 8;

    /** NEWS TITLE */
    public static final int maxNewsTitleLength = 100;
    public static final int minNewsTitleLength = 8;

    /** NEWS SHORT TEXT */
    public static final int maxNewsShortTextLength = 100;
    public static final int minNewsShortTextLength = 8;

    /** NEWS LINK TEXT */
    public static final int maxNewsLinkTextLength = 100;
    public static final int minNewsLinkTextLength = 8;

    /** NEWS FULL TEXT */
    public static final int maxNewsFullTextLength = 100;
    public static final int minNewsFullTextLength = 8;

    /** EMAIL */
    public static final int maxEmailLength = 100;

    /** CONTACT MESSAGE */
    public static final int minContactMessageLength = 10;
    public static final int maxContactMessageLength = 300;

    /** CONTACT SUBJECT */
    public static final int minSubjectLength = 3;
    public static final int maxSubjectLength = 30;

    /** DISCIPLINE */
    public static final int minDisciplineLength = 3;
    public static final int maxDisciplineLength = 30;

    /** GENDER */
    public static final int minGenderLength = 3;
    public static final int maxGenderLength = 30;

    /** EVENT TITLE */
    public static final int minEventTitleLength = 3;
    public static final int maxEventTitleLength = 30;

    /** CITY */
    public static final int minCityLength = 3;
    public static final int maxCityLength = 30;

    /** EVENT HOUSE NUMBER */
    public static final int minEventNumberLength = 0;
    public static final int maxEventNumberLength = 1000000;

    /** STREET  */
    public static final int minStreetLength = 3;
    public static final int maxStreetLength = 30;

    /** POSTAL CODE */
    public static final int minPostalLength = 3;
    public static final int maxPostalLength = 30;


    /** EVENT PEOPLE NUMBER */
    public static final int minEventPeopleLength = 3;
    public static final int maxEventPeopleLength = 20;

    /** EVENT AGE */
    public static final int minEventMinAge = 3;
    public static final int maxEventMinAge = 100;

    /** EVENT AGE */
    public static final int minEventMaxAge = 3;
    public static final int maxEventMaxAge = 100;

    /** EVENT COST */
    public static final int minEvenCost = 3;
    public static final int maxEventCost = 1000000;

    /** EVENT DESCRIPTION */
    public static final int minEventDescriptionLength = 3;
    public static final int maxEventDescriptionLength = 30;


    /** CONTENT TYPE */
    public static final List<String> acceptedImagesTypes = List.of(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/tiff",
            "image/gif",
            "image/raw"
    );




}
