package com.tkachuk.jobnetwork.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachuk.jobnetwork.model.Company;
import com.tkachuk.jobnetwork.repository.CompanyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    Company company1 = new Company(1L, "firstCompany");
    Company company2 = new Company(2L,"secondCompany");
    Company company3 = new Company(3L,"thirdCompany");

    @Test
    public void getCompany() throws Exception {
        Mockito.when(companyRepository.findById(company1.getId())).thenReturn(java.util.Optional.of(company1));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/companies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("firstCompany")));    }

    @Test
    public void getAllCompany() throws Exception {
        List<Company> companies = new ArrayList<Company>(Arrays.asList(company1, company2, company3));
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/companies/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("thirdCompany")));
    }
    @Test
    public void createCompanySuccessful() throws Exception {
        Company company = new Company("newCompany");

        Mockito.when(companyRepository.save(company)).thenReturn(company);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/companies/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(company));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void deleteCompanySuccess() throws Exception {
        Mockito.when(companyRepository.findById(company2.getId())).thenReturn(Optional.of(company2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/companies/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}