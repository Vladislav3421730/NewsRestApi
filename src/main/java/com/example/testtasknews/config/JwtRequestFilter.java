package com.example.testtasknews.config;

import com.example.testtasknews.dto.error.AppErrorDto;
import com.example.testtasknews.utils.Constants;
import com.example.testtasknews.utils.JwtAccessTokenUtils;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Security filter for validating JWT tokens in incoming HTTP requests.
 * <p>
 * Extracts and validates JWT tokens, sets authentication in the security context.
 */
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtAccessTokenUtils jwtAccessTokenUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(Constants.AUTHORIZATION_HEADER);
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith(Constants.BEARER)) {
            jwt = authHeader.substring(7);
            try {
                log.info("Try getting token");
                username = jwtAccessTokenUtils.getUsername(jwt);
            } catch (ExpiredJwtException e) {
                log.error("Token expiration time has passed");
                handleException(response, "Token expiration time has passed", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (SignatureException e) {
                log.error("Invalid signature");
                handleException(response, "Invalid signature", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (MalformedJwtException e) {
                log.error("Invalid token");
                handleException(response, "Invalid token", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (UnsupportedJwtException e) {
                log.error("Token format is not supported");
                handleException(response, "Token format is not supported", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (IllegalArgumentException e) {
                log.error("Token is empty or invalid");
                handleException(response, "Token is empty or invalid", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            Long userId = jwtAccessTokenUtils.getId(jwt);
            CustomUserDetails customUserDetails = new CustomUserDetails(userId, username);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    customUserDetails,
                    null,
                    jwtAccessTokenUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper()
                .writeValueAsString(new AppErrorDto(message,status)));
    }
}
