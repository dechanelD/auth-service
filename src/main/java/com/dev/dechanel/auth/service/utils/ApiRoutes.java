package com.dev.dechanel.auth.service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiRoutes {

    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT ="/logout";
    public static final String ME = "/me";
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/{userId}";
    public static final String USER_BY_EMAIL = "/email/{email}";

}
