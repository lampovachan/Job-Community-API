package com.tkachuk.jobnetwork.testdata;

import com.tkachuk.jobnetwork.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.tkachuk.jobnetwork.web.AbstractControllerTest.REST_URL;

public class UserTestData {
    public static final String USERS_URL = REST_URL + "users/";

    public static final User USER = new User("user", "email@gmail.com", "user");

    public static Map<String, Object> getCreatedUser() {
        return getStringObjectMapUser("user1", "user@email.com", "password");
    }

    private static Map<String, Object> getStringObjectMapUser(String name, String email, String password) {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("email", email);
            put("password", password);
        }};
    }
}
