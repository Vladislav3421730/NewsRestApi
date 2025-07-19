package com.example.testtasknews.utils;

import com.example.testtasknews.exception.AccessDeniedException;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static com.example.testtasknews.model.enums.Role.ROLE_ADMIN;

@UtilityClass
@Slf4j
public class CheckPrivacy {

    public void checkPrivacy(Long createdBy) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (!authorities.contains(new SimpleGrantedAuthority(ROLE_ADMIN.name())) &&
                !createdBy.equals(user.getId())) {
            log.error("Access is forbidden for user {}", user.getUsername());
            throw new AccessDeniedException("You can update/delete only your news on comments");
        }

    }
}
