package com.itlab.group3.controllers.security;

import com.itlab.group3.dao.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;

public interface UserAgent {
    Optional<User> getUser();

    Optional<Authentication> authentication();

    Collection<? extends GrantedAuthority> authorities();

    boolean hasAuthority(final GrantedAuthority authority);

    boolean isUser();

    boolean isAdmin();
}
