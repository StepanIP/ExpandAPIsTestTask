package com.example.expandapitesttask.controller;

import com.example.expandapitesttask.ExpandApiTestTaskApplication;
import com.example.expandapitesttask.dto.Record;
import com.example.expandapitesttask.dto.request.DataRequest;
import com.example.expandapitesttask.dto.request.UserRequest;
import com.example.expandapitesttask.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes= ExpandApiTestTaskApplication.class)
@Transactional
public class ProductControllerTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void testAddUser() throws Exception {
        UserRequest signUpRequest = new UserRequest("test@gmail.com", "password");
        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signUpRequest)));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "password")
    void testAddProducts() throws Exception {
        DataRequest dataRequest = createTestDataRequest();

        ResultActions resultActions = mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dataRequest))).
        andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "password")
    void testGetProducts() throws Exception {
        DataRequest dataRequest = createTestDataRequest();

        ResultActions resultAction = mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dataRequest)));

        List<Record> records = Collections.singletonList(new Record("01-01-2023", "code1", "item1", "10", "Active"));

        ResultActions resultActions = mockMvc.perform(post("/products/all")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    private DataRequest createTestDataRequest() {
        return new DataRequest("test_table", Collections.singletonList(new Record("01-01-2023", "code1", "item1", "10", "Active")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
