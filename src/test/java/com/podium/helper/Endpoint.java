package com.podium.helper;

public class Endpoint {

    /**POST -  jwtRequest body requested*/
    public static final String authenticateEndpoint = "/authenticate";

    /**POST - singUpRequest body requested */
    public static final String addUserEndpoint = "/user/add";

    /**DELETE -  Username path variable requested */
    public static final String deleteUserEndpoint = "/user/{username}";



}
