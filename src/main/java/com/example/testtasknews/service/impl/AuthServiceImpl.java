package com.example.testtasknews.service.impl;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;
import com.example.testtasknews.exception.LoginFailedException;
import com.example.testtasknews.exception.RegistrationFailedException;
import com.example.testtasknews.mapper.CreateUserMapper;
import com.example.testtasknews.model.User;
import com.example.testtasknews.repository.UserRepository;
import com.example.testtasknews.service.AuthService;
import com.example.testtasknews.utils.JwtAccessTokenUtils;
import com.example.testtasknews.utils.PasswordGenerator;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenUtils jwtAccessTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public JwtResponseDto createAuthToken(LoginUserRequestDto user) {

        CustomUserDetails userDetails;

        try {
            log.info("Attempting authentication for user: {}", user.getUsername());
            userDetails = (CustomUserDetails) authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())).getPrincipal();
        } catch (BadCredentialsException badCredentialsException) {
            log.error("error: {}", badCredentialsException.getMessage());
            throw new LoginFailedException("invalid username or password for user " + user.getUsername());
        }

        log.info("User {} authenticated successfully", user.getUsername());
        return new JwtResponseDto(jwtAccessTokenUtils.generateAccessToken(userDetails));
    }

    @Override
    @Transactional
    public void registerUser(CreateUserRequestDto createUserRequestDto) {

        if(userRepository.existsByUsername(createUserRequestDto.getUsername())) {
            log.error("Username {} is already in use", createUserRequestDto.getUsername());
            throw new RegistrationFailedException("User with login " + createUserRequestDto.getUsername() + " already exists in the system");
        }

        User user = CreateUserMapper.map(createUserRequestDto);
        String password = PasswordGenerator.generateRandomPassword(8);

        log.info("Password for user {}: {}", user.getUsername(), password);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
