package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateUserRequestDto;
import com.example.testtasknews.dto.request.LoginUserRequestDto;
import com.example.testtasknews.dto.response.JwtResponseDto;

public interface AuthService {

    JwtResponseDto createAuthToken(LoginUserRequestDto user);

    void registerUser(CreateUserRequestDto user);

}
