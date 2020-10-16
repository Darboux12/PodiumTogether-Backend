package com.podium.helper;

public class Endpoint {

    /** POST -  jwtRequest body requested*/
    public static final String authenticateEndpoint = "/authenticate";

    /** POST - singUpRequest body requested */
    public static final String addUserEndpoint = "/user/add";

    /** DELETE -  Username path variable requested */
    public static final String deleteUserEndpoint = "/user/{username}";

    /** GET - Username path variable requested */
    public static final String getUserByUsernameEndpoint = "/user/find/{username}";

    /** GET */
    public static final String findAllUsers = "/user/find/all";

    /** GET */
    public static final String findAllNews = "/news/find/all";

    /** GET */
    public static final String findNewsByTitle = "/news/find/title/{title}";

    /** POST - Params required: title, shortText, linkText, fullText, image */
    public static final String addNews = "/news/add";

    /** POST - ContactRequest body requested */
    public static final String addContact = "/contact/add";

    /** GET - Params: userEmail, message, subject */
    public static final String findContact = "/contact/find";

    /** DELETE - Id path variable requested*/
    public static final String deleteContact = "/contact/delete/{id}";

    /** POST - Name param requested */
    public static final String addSubject = "/subject/add";

    /** DELETE - Name path variable requested */
    public static final String deleteSubject = "/subject/delete/{name}";

    /** GET - Name param requested */
    public static final String findSubjectByName = "/subject/find/{name}";

    /** GET */
    public static final String findAllCountry = "/country/find/all";

    /** DELETE - Name path variable requested */
    public static final String deleteNewsById = "/news/delete/{id}";





























}
