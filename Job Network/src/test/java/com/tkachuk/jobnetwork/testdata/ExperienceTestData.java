package com.tkachuk.jobnetwork.testdata;

import com.tkachuk.common.dto.ExperienceDto;
import com.tkachuk.jobnetwork.model.Experience;
import com.tkachuk.jobnetwork.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.tkachuk.jobnetwork.testdata.UserTestData.USERS_URL;
import static com.tkachuk.jobnetwork.web.AbstractControllerTest.REST_URL;

public class ExperienceTestData {
    public static final String EXPERIENCE_URL = REST_URL + USERS_URL + "experience";
    public static final ExperienceDto EXPERIENCE = new ExperienceDto(LocalDate.of(2020, 6, 29).toEpochDay(),
            LocalDate.of(2021, 7,21).toEpochDay(), 1L);

    public static Map<String, Object> getStringObjectMapExperience(User user, LocalDate date, Long companyId) {
        return new HashMap<String, Object>() {{
            put("user", USERS_URL + user.getId());
            put("date", date);
            put("companyId", companyId);
        }};
    }
}
