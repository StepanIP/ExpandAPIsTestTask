package com.example.expandapitesttask.controller;

import com.example.expandapitesttask.ExpandApiTestTaskApplication;
import com.example.expandapitesttask.dto.request.UserRequest;
import com.example.expandapitesttask.dto.response.JwtAuthenticationResponse;
import com.example.expandapitesttask.service.security.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes= ExpandApiTestTaskApplication.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    void testAddUser() throws Exception {
        UserRequest signUpRequest = new UserRequest("test@gmail.com", "password");

        mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signUpRequest)))
                .andExpect(status().isCreated());

        verify(authenticationService, times(1)).signup(signUpRequest);
    }

    @Test
    void testAuthenticateUser() throws Exception {
        UserRequest signInRequest = new UserRequest("test@gmail.com", "password");

        JwtAuthenticationResponse response = new JwtAuthenticationResponse("token");

        when(authenticationService.signin(signInRequest)).thenReturn(response);

        mockMvc.perform(post("/user/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signInRequest)))
                .andExpect(status().isOk());

        verify(authenticationService, times(1)).signin(signInRequest);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}