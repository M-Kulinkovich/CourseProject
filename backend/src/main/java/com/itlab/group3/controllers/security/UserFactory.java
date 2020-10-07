package com.itlab.group3.controllers.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public UserAgent getInstance(Authentication authentication) {
        return new CurrentUser(authentication);
    }
}
