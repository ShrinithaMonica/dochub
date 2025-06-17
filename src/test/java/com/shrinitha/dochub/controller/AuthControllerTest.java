package com.shrinitha.dochub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrinitha.dochub.dto.LoginRequest;
import com.shrinitha.dochub.dto.LoginResponse;
import com.shrinitha.dochub.dto.RegisterRequest;
import com.shrinitha.dochub.exception.GlobalExceptionHandler;
import com.shrinitha.dochub.service.AuthService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;
    private AuthService authService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        objectMapper = new ObjectMapper();

        AuthController controller = new AuthController();
        controller.authService = authService;
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("john");
        request.setPassword("pass123");

        request.setRoles(Set.of("USER"));

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());

        verify(authService).register(any(RegisterRequest.class));
    }

    @Test
    void shouldLoginUserAndReturnToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("john");
        request.setPassword("pass123");

        LoginResponse response = new LoginResponse("mocked-jwt-token");

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    void shouldReturn400OnInvalidRegisterRequest() throws Exception {
        // Empty payload (violates all constraints)
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400OnInvalidLoginRequest() throws Exception {
        // Empty payload
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidationErrorsForMissingFields() throws Exception {
        RegisterRequest request = new RegisterRequest(); // all fields null

        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isArray()).andExpect(jsonPath("$.errors[*]").value(Matchers.hasItem("Username is required"))).andExpect(jsonPath("$.errors[*]").value(Matchers.hasItem("Password is required"))).andExpect(jsonPath("$.errors[*]").value(Matchers.hasItem("Roles are required")));
    }

}
