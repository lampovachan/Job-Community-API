package com.tkachuk.jobnetwork.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachuk.common.dto.CompanyDto;
import com.tkachuk.jobnetwork.controller.CompanyController;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import com.tkachuk.jobnetwork.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CompanyRepository companyRepository;

    Company company1 = new Company("firstCompany");
    Company company2 = new Company("secondCompany");
    Company company3 = new Company("thirdCompany");

    @Test
    public void getCompany() throws Exception {
//        List<Company> companies = new ArrayList<>(Arrays.asList(company1, company2, company3));
        Mockito.when(companyRepository.findById(company1.getId())).thenReturn(java.util.Optional.of(company1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/companies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("firstCompany"));
    }
}
