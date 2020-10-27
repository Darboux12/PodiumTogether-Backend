package com.podium.constant;

public class PodiumEndpoint {

    /** POST | Sign in user | JwtRequest body required */
    public static final String authenticate = "/authenticate";

    /** POST | Check if password is correct for user | JwtRequest body required */
    public static final String authenticateNoToken = "/authenticate/check";

    /** POST | Sign up user | SignUpRequest body required */
    public static final String addUser = "/user/add";

    /** DELETE | Delete user by username | Username path variable required */
    public static final String deleteUser = "/user/delete/{username}";

    /** GET | Find user by username | Username path variable required */
    public static final String findUserByUsername = "/user/find/{username}";

    /** GET | Find all users */
    public static final String findAllUsers = "/user/find/all";

    /** GET | Exist user by username | Username path variable required */
    public static final String existUserByUsername = "/user/exist/username/{username}";

    /** GET | Exist user by email | Email path variable required */
    public static final String existUserByEmail = "/user/exist/email/{email}";


    public static final String updateUserProfile = "/user/update/profile";






    /** GET | Find all news */
    public static final String findAllNews = "/news/find/all";

    /** GET | Find news by title | Title path variable required */
    public static final String findNewsByTitle = "/news/find/title/{title}";

    /** GET | Find news by id | Id path variable required */
    public static final String findNewsById = "/news/find/id/{id}";

    /** POST | Add news | NewsRequestDto body required */
    public static final String addNews = "/news/add";

    /** DELETE | Delete news by id | News path variable required */
    public static final String deleteNewsById = "/news/delete/{id}";

    /** POST | Add news image | Title and images params required */
    public static final String addNewsImage = "/image/upload/news";



    /** POST | Add contact | ContactRequestDto body required */
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

    /** GET - Name param requested */
    public static final String findAllSubject =  "/subject/find/all";

    /** GET - Name param requested */
    public static final String existSubjectByName =  "/subject/exist/{name}";







    public static final String findAllContact = "/contact/find/all";

    public static final String findAllContactByEmail = "/contact/find/email/{email}";

    public static final String findAllContactBySubject = "/contact/find/subject/{subject}";







    public static final String addDiscipline = "/discipline/add";
    public static final String findAllDiscipline = "/discipline/find/all";
    public static final String existDisciplineByName = "/discipline/exist/{discipline}";
    public static final String deleteDisciplineByName = "/discipline/delete/{discipline}";
    public static final String findByDisciplineName = "/discipline/find/{discipline}";


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







































}
