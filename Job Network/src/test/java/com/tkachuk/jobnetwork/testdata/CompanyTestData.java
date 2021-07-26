package com.tkachuk.jobnetwork.testdata;

import com.tkachuk.common.dto.CompanyDto;
import com.tkachuk.jobnetwork.model.Company;

import java.util.HashMap;
import java.util.Map;

import static com.tkachuk.jobnetwork.web.AbstractControllerTest.REST_URL;

public class CompanyTestData {
    public static final String COMPANY_URL = REST_URL + "companies/";

    public static final CompanyDto COMPANY_0 = new CompanyDto("Computools");

    public static Map<String, Object> getStringObjectMapCompany(String name) {
        return new HashMap<String, Object>() {{
            put("name", name);
        }};
    }
}
