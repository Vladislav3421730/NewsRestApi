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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> createToken(@RequestBody @Valid LoginUserRequestDto userDto) {
        JwtResponseDto jwtResponseDto = authService.createAuthToken(userDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid CreateUserRequestDto userDto) {
        authService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
