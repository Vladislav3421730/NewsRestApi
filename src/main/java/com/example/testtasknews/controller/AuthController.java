package com.example.testtasknews.controller;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;
import com.example.testtasknews.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication and user registration endpoints.
 * <p>
 * Provides endpoints for user login (JWT token generation) and registration.
 * Base path: /api/v1/auth
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticates a user and returns a JWT token if credentials are valid.
     *
     * @param userDto login credentials (username and password)
     * @return JWT response containing the access token
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> createToken(@RequestBody @Valid LoginUserRequestDto userDto) {
        JwtResponseDto jwtResponseDto = authService.createAuthToken(userDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

    /**
     * Registers a new user in the system.
     *
     * @param userDto registration data for the new user
     * @return HTTP 201 Created if registration is successful
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid CreateUserRequestDto userDto) {
        authService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
