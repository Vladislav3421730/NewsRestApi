package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;

/**
 * Service interface for authentication and user registration operations.
 * <p>
 * Provides contract for user login (JWT token generation) and registration logic.
 */
public interface AuthService {

    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     *
     * @param user login credentials (username and password)
     * @return JWT response containing the access token
     */
    JwtResponseDto createAuthToken(LoginUserRequestDto user);

    /**
     * Registers a new user in the system.
     *
     * @param user registration data for the new user
     */
    void registerUser(CreateUserRequestDto user);

}
