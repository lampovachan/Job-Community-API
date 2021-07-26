package com.tkachuk.jobnetwork.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tkachuk.jobnetwork.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.tkachuk.jobnetwork.util.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
abstract public class AbstractControllerTest {

    public static final String REST_URL = "/";

    protected static final LocalDate LOCAL_DATE = LocalDate.now();

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    ResultActions testGetAll(String url, User authUser) throws Exception {
        return this.mockMvc.perform(get(url)
                .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    ResultActions testGetIsForbidden(String url, User authUser) throws Exception {
        return this.mockMvc
                .perform(get(url)
                        .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    ResultActions testGetIsNotFound(String url, User authUser) throws Exception {
        return this.mockMvc
                .perform(get(url)
                        .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    ResultActions testCreate(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(post(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    ResultActions testCreateIsConflict(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(post(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    ResultActions testCreateIsForbidden(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(post(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    ResultActions testUpdate(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(patch(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isOk());
    }

    ResultActions testUpdateIsConflict(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(patch(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    ResultActions testUpdateIsForbidden(String url, User authUser, String body) throws Exception {
        return mockMvc.perform(patch(url)
                .with(userHttpBasic(authUser))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    ResultActions testDelete(String url, User authUser) throws Exception {
        return mockMvc.perform(delete(url)
                .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    ResultActions testDeleteIsForbidden(String url, User authUser) throws Exception {
        return mockMvc.perform(delete(url)
                .with(userHttpBasic(authUser)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    abstract void getAll() throws Exception;

    @Test
    abstract void getIsNotFound() throws Exception;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    abstract void create() throws Exception;

    @Test
    abstract void createIsConflict() throws Exception;

    @Test
    abstract void createIsForbidden() throws Exception;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    abstract void update() throws Exception;

    @Test
    abstract void updatedIsConflict() throws Exception;

    @Test
    abstract void updateIsForbidden() throws Exception;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    abstract void deleted() throws Exception;

    @Test
    abstract void deletedIsForbidden() throws Exception;
}
