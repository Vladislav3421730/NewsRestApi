package com.example.testtasknews.model.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enumeration of user roles in the system.
 * <p>
 * Defines authorities for access control (ADMIN, JOURNALIST, SUBSCRIBER).
 */
public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_JOURNALIST, ROLE_SUBSCRIBER;

    @Override
    public String getAuthority() {
        return name();
    }
}
