package com.example.testtasknews.service.impl;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.request.TokenRefreshRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;
import com.example.testtasknews.exception.LoginFailedException;
import com.example.testtasknews.exception.RegistrationFailedException;
import com.example.testtasknews.repository.UserRepository;
import com.example.testtasknews.service.AuthService;
import com.example.testtasknews.utils.JwtAccessTokenUtils;
import com.example.testtasknews.wrapper.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenUtils jwtAccessTokenUtils;
    private final UserRepository userRepository;

    @Override
    public JwtResponseDto createAuthToken(LoginUserRequestDto user) {

        CustomUserDetails userDetails = null;

        try {
            log.info("Attempting authentication for user: {}", user.getUsername());
            userDetails = (CustomUserDetails) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())).getDetails();
        } catch (BadCredentialsException badCredentialsException) {
            log.error("error: {}", badCredentialsException.getMessage());
            throw new LoginFailedException("invalid username or password for user " + user.getUsername());
        }

        log.info("User {} authenticated successfully", user.getUsername());
        return new JwtResponseDto(jwtAccessTokenUtils.generateAccessToken(userDetails));
    }

    @Override
    public void registerUser(CreateUserRequestDto user) {
        if(userRepository.existsByUsername(user.getUsername())) {
            log.error("Username {} is already in use", user.getUsername());
            throw new RegistrationFailedException("User with login " + user.getUsername() + " already exists in the system");
        }


    }
}
