package com.itlab.group3.controllers.security;

import com.itlab.group3.dao.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.itlab.group3.dao.model.Role.ADMIN;
import static com.itlab.group3.dao.model.Role.USER;

@RequiredArgsConstructor
public class CurrentUser implements UserAgent {

    @NonNull
    private final Authentication authentication;

    @Override
    public Optional<Authentication> authentication() {
        return Optional.of(authentication);
    }

    @Override
    public Optional<User> getUser() {
        return authentication()
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof User)
                .map(principal -> (User) principal);
    }

    @Override
    public Collection<? extends GrantedAuthority> authorities() {
        return authentication()
                .map(Authentication::getAuthorities)
                .orElse(Collections.emptyList());
    }

    @Override
    public boolean hasAuthority(final GrantedAuthority authority) {
        return authorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority.getAuthority()));
    }

    @Override
    public boolean isUser() {
        return hasAuthority(USER);
    }

    @Override
    public boolean isAdmin() {
        return hasAuthority(ADMIN);
    }
}
