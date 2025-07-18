package com.example.testtasknews.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_JOURNALIST, ROLE_SUBSCRIBER;

    @Override
    public String getAuthority() {
        return name();
    }
}
