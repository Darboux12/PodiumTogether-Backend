package com.podium.constant;

import com.podium.controller.dto.other.PodiumCompatibilityEndpoint;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PodiumEndpoint {

    // CITY

    /** GET | Find all cities */
    public static final String findAllCity = "/city/find/all";

    /** POST | Add city | CityAddRequestDto body required */
    public static final String addCity = "/city/add";

    /** GET | Exist city by name | City name path variable required */
    public static final String existCityByName = "/city/exist/{name}";

    /** DELETE | Delete city by name | City name path variable required*/
    public static final String deleteCityByName = "/city/delete/{name}";

    /** GET | Find city by name | City name path variable required */
    public static final String findCityByName = "/city/find/name/{name}";

    // CONTACT

    /** POST | Add contact | ContactAddRequestDto body required */
    public static final String addContact = "/contact/add";

    /** GET | Find all contact */
    public static final String findAllContact = "/contact/find/all";

    /** GET | Find all contact by user email | Email path variable required */
    public static final String findAllContactByEmail = "/contact/find/all/email/{email}";

    // SUBJECT

    /** GET | Find all contact by subject | Email path variable required */
    public static final String findAllContactBySubject = "/contact/find/all/subject/{subject}";

    /** DELETE - Id path variable requested*/
    public static final String deleteContact = "/contact/delete/{id}";

    /** POST - Name param requested */
    public static final String addSubject = "/subject/add";

    /** DELETE - Name path variable requested */
    public static final String deleteSubject = "/subject/delete/{name}";

    /** GET | Find subject by name | Name param requested */
    public static final String findSubjectByName = "/subject/find/{name}";

    /** GET | Find all subjects */
    public static final String findAllSubject =  "/subject/find/all";

    /** GET - Name param requested */
    public static final String existSubjectByName =  "/subject/exist/{name}";

    // DISCIPLINE

    /** POST | Add discipline | DisciplineAddRequestDto body required */
    public static final String addDiscipline = "/discipline/add";

    /** GET | Find all discipline */
    public static final String findAllDiscipline = "/discipline/find/all";

    /** GET | Exist discipline by name | discipline path variable required */
    public static final String existDisciplineByName = "/discipline/exist/{discipline}";

    /** GET | Find discipline by name | discipline path variable required */
    public static final String findByDisciplineName = "/discipline/find/{discipline}";

    /** Delete | Delete discipline by name | discipline path variable required */
    public static final String deleteDisciplineByName = "/discipline/delete/{discipline}";

    // GENDER

    /** GET | Find all gender */
    public static final String findAllGender = "/gender/find/all";

    /** POST | Add gender | GenderAddRequestDto body required */
    public static final String addGender = "/gender/add";

    /** GET | Exist gender by name | name path variable required */
    public static final String existGenderByName = "/gender/exist/{name}";

    /** Delete | Delete gender by name | name path variable required */
    public static final String deleteGenderByName = "/gender/delete/{name}";

    /** GET | gender by name | name path variable required */
    public static final String findGenderByName = "/gender/find/{name}";

    // AUTHENTICATION

    /** POST | Sign in user | JwtRequest body required */
    public static final String authenticate = "/authenticate";

    /** POST | Check if password is correct for user | JwtRequest body required */
    public static final String authenticateNoToken = "/authenticate/check";

    /** POST | Find username from token | token param required */
    public static final String findUsernameFromToken = "/token/find/username";

    /** POST | Find token expiration date | token param required  */
    public static final String findTokenExpirationDate = "/token/find/expiration";

    // USER

    /** POST | Sign up user | SignUpRequest body required */
    public static final String addUser = "/user/add";

    /** DELETE | Delete user by username | Username path variable required */
    public static final String deleteUser = "/user/delete/{username}";

    /** GET | Find user by username | Username path variable required */
    public static final String findUserByUsername = "/user/find/username/{username}";

    /** GET | Find all users */
    public static final String findAllUsers = "/user/find/all";

    /** GET | Exist user by username | Username path variable required */
    public static final String existUserByUsername = "/user/exist/username/{username}";

    /** GET | Exist user by email | Email path variable required */
    public static final String existUserByEmail = "/user/exist/email/{email}";

    public static final String grantUserRole = "/user/grant/role";

    public static final String degradeUserRole = "/user/degrade/role";

    public static final String synchronizeResources = "/resources/synchronize";





    // WEEK DAY

    /** GET | Find all week day */
    public static final String findAllWeekDay = "/weekday/find/all";

    // RATING CATEGORY

    /** GET | Find all rating categories */
    public static final String findAllRatingCategories = "/rating/category/find/all";

    /** POST | Add rating category | RatingCategoryRequest body required */
    public static final String addRatingCategory = "/rating/category/add";

    /** GET | Exist rating category by category | Category path variable required */
    public static final String existRatingCategory = "/rating/category/exist/{category}";

    /** DELETE | Delete rating category by category | Category path variable required */
    public static final String deleteRatingCategory = "/rating/category/delete/{category}";

    /** GET | Find rating category by category | Category path variable required */
    public static final String findRatingCategory = "/rating/category/find/{category}";

    public static final String findServerAddress = "/server/address";

    public static final String findServerEndpoints = "/server/endpoints";

    public static final String findServerEndpointsCompatibility = "/server/endpoints/compatibility";

    public static final String updateUserProfile = "/user/update/profile";

    public static final String uploadUserProfileImage = "/image/upload/profile";

    /** GET | Find all news */
    public static final String findAllNews = "/news/find/all";

    /** GET | Find news by title | Title path variable required */
    public static final String findNewsByTitle = "/news/find/title/{title}";

    /** GET | Find news by id | Id path variable required */
    public static final String findNewsById = "/news/find/id/{id}";

    /** POST | Add news | NewsAddRequestDto body required */
    public static final String addNews = "/news/add";

    /** DELETE | Delete news by id | News path variable required */
    public static final String deleteNewsById = "/news/delete/{id}";

    /** POST | Add news image | Title and images params required */
    public static final String addNewsImage = "/image/upload/news";

    /** GET */
    public static final String findAllCountry = "/country/find/all";
    public static final String addCountry = "/country/add";
    public static final String existCountryByName = "/country/exist/{name}";
    public static final String deleteCountryByName = "/country/delete/{name}";
    public static final String findCountryByName= "/country/find/name/{name}";

    public static final String addEvent= "/event/add";

    public static final String addPlace = "/place/add";

    public static final String findPlaceByName = "/place/find/name/{name}";

    public static final String findAllPlaces = "/place/find/all";

    public static final String findPlaceById = "/place/find/id/{id}";

    public static final String deletePlaceById = "/place/delete/id/{id}";

    public static final String deleteEvent = "/event/delete/{title}";

    public static final String addReview = "/place/review/add";

    public static final String deleteReviewById = "/place/review/delete/id/{id}";

    public static final String findReviewsByAuthor = "/place/review/find/author/{username}";

    public static final String incrementReviewLikes = "/place/review/increment/likes/{id}";

    public static final String incrementReviewDislikes = "/place/review/increment/dislikes/{id}";

    public static final String uploadEventImages= "/image/upload/event";

    public static final String uploadEventFiles = "/file/upload/event";

    public static List<PodiumCompatibilityEndpoint> getAllEndpoints(){

        var endpointsList = new ArrayList<PodiumCompatibilityEndpoint>();

        Field[] fields = PodiumEndpoint.class.getDeclaredFields();

        Arrays.asList(fields).forEach(field -> {

            try {
                endpointsList.add(new PodiumCompatibilityEndpoint(field.getName(),(String)field.get(PodiumEndpoint.class.getDeclaredConstructor().newInstance())));
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

        });

        return endpointsList;

    }

}
