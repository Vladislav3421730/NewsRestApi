package com.example.testtasknews.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for JWT authentication responses.
 * <p>
 * Contains the JWT access token returned after successful authentication.
 */
@Data
@AllArgsConstructor
public class JwtResponseDto {
    private String accessToken;
}
