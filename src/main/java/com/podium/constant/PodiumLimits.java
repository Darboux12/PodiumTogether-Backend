package com.podium.constant;

import java.util.List;

public class PodiumLimits {

    /** USERNAME */
    public static final int maxUsernameLength = 50;
    public static final int minUsernameLength = 3;

    /** PASSWORD*/
    public static final int maxPasswordLength = 100;
    public static final int minPasswordLength = 8;

    /** NEWS TITLE */
    public static final int maxNewsTitleLength = 200;
    public static final int minNewsTitleLength = 8;

    /** NEWS SHORT TEXT */
    public static final int maxNewsShortTextLength = 300;
    public static final int minNewsShortTextLength = 8;

    /** NEWS LINK TEXT */
    public static final int maxNewsLinkTextLength = 150;
    public static final int minNewsLinkTextLength = 8;

    /** NEWS FULL TEXT */
    public static final int maxNewsFullTextLength = 10000;
    public static final int minNewsFullTextLength = 8;

    /** IMAGES NUMBER */
    public static final int maxImagesNumber = 20;

    /** EMAIL */
    public static final int maxEmailLength = 150;

    /** CONTACT MESSAGE */
    public static final int minContactMessageLength = 10;
    public static final int maxContactMessageLength = 10000;

    /** CONTACT SUBJECT */
    public static final int minSubjectLength = 3;
    public static final int maxSubjectLength = 50;

    /** DISCIPLINE */
    public static final int minDisciplineLength = 3;
    public static final int maxDisciplineLength = 50;

    /** GENDER */
    public static final int minGenderLength = 3;
    public static final int maxGenderLength = 50;

    /** EVENT TITLE */
    public static final int minEventTitleLength = 3;
    public static final int maxEventTitleLength = 200;

    /** OBJECT NAME */
    public static final int minObjectNameLength = 3;
    public static final int maxObjectNameLength = 200;

    /** CITY */
    public static final int minCityLength = 3;
    public static final int maxCityLength = 200;

    /** HOUSE NUMBER */
    public static final int minBuildingNumberLength = 0;
    public static final int maxBuildingNumberLength = 1000000;

    /** STREET  */
    public static final int minStreetLength = 3;
    public static final int maxStreetLength = 30;

    /** POSTAL CODE */
    public static final int minPostalLength = 3;
    public static final int maxPostalLength = 30;

    /** EVENT PEOPLE NUMBER */
    public static final int minEventPeopleLength = 3;
    public static final int maxEventPeopleLength = 20;

    /** EVENT MIN AGE */
    public static final int minEventMinAge = 3;
    public static final int maxEventMinAge = 100;

    /** EVENT MAX AGE */
    public static final int minEventMaxAge = 3;
    public static final int maxEventMaxAge = 100;

    /** OBJECT MIN AGE */
    public static final int minObjectMinAge = 3;
    public static final int maxObjectMinAge = 100;

    /** OBJECT MAX AGE */
    public static final int minObjectMaxAge = 3;
    public static final int maxObjectMaxAge = 100;

    /** COST */
    public static final int minCost = 3;
    public static final int maxCost = 1000000;

    /** EVENT DESCRIPTION */
    public static final int minEventDescriptionLength = 3;
    public static final int maxEventDescriptionLength = 10000;


    /** CONTENT TYPE */
    public static final List<String> acceptedImagesTypes = List.of(
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/tiff",
            "image/gif",
            "image/raw"
    );

    /** AGE LIMIT */
    public static final int minSignUpAge = 13;

    public static final int maxUserDescriptionLength = 500;




}
