package com.example.testtasknews.controller;

import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;
import com.example.testtasknews.exception.GlobalExceptionHandler;
import com.example.testtasknews.exception.LoginFailedException;
import com.example.testtasknews.exception.RegistrationFailedException;
import com.example.testtasknews.service.AuthService;
import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerUnitTest {

    private static MockMvc mockMvc;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static AuthService authService;
    private static LoginUserRequestDto validLoginRequest;
    private static JwtResponseDto validJwtResponse;
    private static CreateUserRequestDto validRegisterRequest;
    private static CreateUserRequestDto invalidRegisterRequestEmptyUsername;
    private static CreateUserRequestDto invalidRegisterRequestEmptyName;
    private static CreateUserRequestDto validRegisterRequestForFail;
    private static LoginUserRequestDto invalidLoginRequestEmptyUsername;
    private static LoginUserRequestDto invalidLoginRequestEmptyPassword;
    private static String invalidLoginJson;
    private static String invalidRegisterJson;

    @BeforeEach
    void setup() {
        authService = Mockito.mock(AuthService.class);
        AuthController authController = new AuthController(authService);

        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        validLoginRequest = new LoginUserRequestDto("user123", "q1w2e3");
        validJwtResponse = new JwtResponseDto("token");

        validRegisterRequest = new CreateUserRequestDto();
        validRegisterRequest.setUsername("user123");
        validRegisterRequest.setRole("journalist");
        validRegisterRequest.setName("Ivan");
        validRegisterRequest.setSurname("Ivanov");
        validRegisterRequest.setParentName("Ivanovich");

        invalidRegisterRequestEmptyUsername = new CreateUserRequestDto();
        invalidRegisterRequestEmptyUsername.setUsername("");
        invalidRegisterRequestEmptyUsername.setRole("ADMIN");
        invalidRegisterRequestEmptyUsername.setName("Ivan");
        invalidRegisterRequestEmptyUsername.setSurname("Ivanov");
        invalidRegisterRequestEmptyUsername.setParentName("Ivanovich");

        invalidRegisterRequestEmptyName = new CreateUserRequestDto();
        invalidRegisterRequestEmptyName.setUsername("user123");
        invalidRegisterRequestEmptyName.setRole("USER");
        invalidRegisterRequestEmptyName.setName(""); 
        invalidRegisterRequestEmptyName.setSurname("Ivanov");
        invalidRegisterRequestEmptyName.setParentName("Ivanovich");

        validRegisterRequestForFail = new CreateUserRequestDto();
        validRegisterRequestForFail.setUsername("user123");
        validRegisterRequestForFail.setRole("USER");
        validRegisterRequestForFail.setName("Ivan");
        validRegisterRequestForFail.setSurname("Ivanov");
        validRegisterRequestForFail.setParentName("Ivanovich");

        invalidLoginRequestEmptyUsername = new LoginUserRequestDto("", "q1w2e3");
        invalidLoginRequestEmptyPassword = new LoginUserRequestDto("user123", "");
        invalidLoginJson = "{\"username\": \"\", \"password\": }";
        invalidRegisterJson = "{\"username\": \"user123\", \"role\": \"USER\", \"name\": 123, \"surname\": \"Ivanov\", \"parentName\": \"Ivanovich\"}";
    }

    @Test
    void shouldReturnJwtToken_whenLoginWithValidData() throws Exception {
        when(authService.createAuthToken(validLoginRequest)).thenReturn(validJwtResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(validJwtResponse.getAccessToken()));
    }

    @Test
    void shouldReturnBadRequest_whenLoginWithEmptyUsername() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidLoginRequestEmptyUsername)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenLoginWithEmptyPassword() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidLoginRequestEmptyPassword)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenLoginWithInvalidJson() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(invalidLoginJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCreated_whenRegisterWithValidData() throws Exception {
        Mockito.doNothing().when(authService).registerUser(validRegisterRequest);
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validRegisterRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequest_whenRegisterWithEmptyUsername() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidRegisterRequestEmptyUsername)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenRegisterWithEmptyPassword() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidRegisterRequestEmptyName)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenRegisterWithInvalidJson() throws Exception {
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(invalidRegisterJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorized_whenLoginFails() throws Exception {
        when(authService.createAuthToken(validLoginRequest))
                .thenThrow(new LoginFailedException("Invalid credentials"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenRegisterFails() throws Exception {
        Mockito.doThrow(new RegistrationFailedException("User already exists"))
                .when(authService).registerUser(validRegisterRequestForFail);
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validRegisterRequestForFail)))
                .andExpect(status().isBadRequest());
    }
}
