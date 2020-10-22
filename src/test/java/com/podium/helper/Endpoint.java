package com.podium.helper;

public class Endpoint {

    /** POST -  jwtRequest body requested*/
    public static final String authenticateEndpoint = "/authenticate";

    /** POST - singUpRequest body requested */
    public static final String addUserEndpoint = "/user/add";

    /** DELETE -  Username path variable requested */
    public static final String deleteUserEndpoint = "/user/delete/{username}";

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

    /** POST - ContactRequestDto body requested */
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



    /** DELETE - Name path variable requested */
    public static final String deleteNewsById = "/news/delete/{id}";

    public static final String addNewsImage = "/image/upload/news";

    public static final String addDiscipline = "/discipline/add";
    public static final String findAllDiscipline = "/discipline/find/all";
    public static final String existDisciplineByName = "/discipline/exist/{discipline}";
    public static final String deleteDisciplineByName = "/discipline/delete/{discipline}";


    /** GET */
    public static final String findAllCountry = "/country/find/all";
    public static final String addCountry = "/country/add";
    public static final String existCountryByName = "/country/exist/{name}";
    public static final String deleteCountryByName = "/country/delete/{name}";

    public static final String findAllGender = "/gender/find/all";
    public static final String addGender = "/gender/add";
    public static final String existGenderByName = "/gender/exist/{name}";
    public static final String deleteGenderByName = "/gender/delete/{name}";
    public static final String findGenderByName = "/gender/find/name/{name}";

    public static final String findAllCity = "/city/find/all";
    public static final String addCity = "/city/add";
    public static final String existCityByName = "/city/exist/{name}";
    public static final String deleteCityByName = "/city/delete/{name}";
    public static final String findCityByName = "/city/find/name/{name}";

    public static final String addEvent= "/event/add";

    public static final String deleteEvent = "/event/delete/{title}";


    public static final String findAllContact = "/contact/find/all";

    public static final String findAllContactByEmail = "/contact/find/email/{email}";

    public static final String findAllContactBySubject = "/contact/find/subject/{subject}";






































}
