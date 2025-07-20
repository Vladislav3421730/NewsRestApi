package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;
import com.example.testtasknews.exception.LoginFailedException;
import com.example.testtasknews.exception.RegistrationFailedException;
import com.example.testtasknews.model.User;
import com.example.testtasknews.repository.UserRepository;
import com.example.testtasknews.service.impl.AuthServiceImpl;
import com.example.testtasknews.utils.JwtAccessTokenUtils;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceJunitTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtAccessTokenUtils jwtAccessTokenUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    private static LoginUserRequestDto validLoginRequest;
    private static CreateUserRequestDto validRegisterRequest;
    private static CustomUserDetails userDetails;
    private static Authentication authentication;
    private static final String JWT_TOKEN = "jwt-token";
    private static final String ENCODED_PASSWORD = "encoded-password";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validLoginRequest = new LoginUserRequestDto("user123", "q1w2e3");
        validRegisterRequest = new CreateUserRequestDto();
        validRegisterRequest.setUsername("user123");
        validRegisterRequest.setRole("ADMIN");
        validRegisterRequest.setName("Ivan");
        validRegisterRequest.setSurname("Ivanov");
        validRegisterRequest.setParentName("Ivanovich");
        userDetails = mock(CustomUserDetails.class);
        authentication = mock(Authentication.class);
    }

    @Test
    void shouldReturnJwtToken_whenLoginWithValidData() {

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtAccessTokenUtils.generateAccessToken(userDetails)).thenReturn(JWT_TOKEN);

        JwtResponseDto response = authService.createAuthToken(validLoginRequest);
        assertThat(response.getAccessToken()).isEqualTo(JWT_TOKEN);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtAccessTokenUtils).generateAccessToken(userDetails);
    }

    @Test
    void shouldThrowLoginFailedException_whenBadCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));
        assertThrows(LoginFailedException.class, () -> authService.createAuthToken(validLoginRequest));
    }

    @Test
    void shouldRegisterUser_whenUsernameNotExists() {
        when(userRepository.existsByUsername(validLoginRequest.getUsername())).thenReturn(false);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

        authService.registerUser(validRegisterRequest);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getUsername()).isEqualTo(validRegisterRequest.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(ENCODED_PASSWORD);
    }

    @Test
    void shouldThrowRegistrationFailedException_whenUsernameExists() {
        when(userRepository.existsByUsername(validLoginRequest.getUsername())).thenReturn(true);
        assertThrows(RegistrationFailedException.class, () -> authService.registerUser(validRegisterRequest));
        verify(userRepository, never()).save(any());
    }
}
