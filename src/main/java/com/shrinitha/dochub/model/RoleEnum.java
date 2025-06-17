package com.shrinitha.dochub.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ADMIN, EDITOR, VIEWER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
