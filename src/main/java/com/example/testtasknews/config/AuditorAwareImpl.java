package com.example.testtasknews.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
//        // Пример: получаем ID пользователя из SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            return Optional.of(userDetails.getId());
//        }
//        // Если пользователь не найден, возвращаем системного (например, ID = 0)
        return Optional.of(0L);
    }
}
