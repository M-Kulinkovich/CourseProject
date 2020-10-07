package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.UserResource;
import com.itlab.group3.controllers.security.UserAgent;
import com.itlab.group3.dao.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserResource> {

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final UserAgent userAgent;

    @Override
    public UserResource toResource(User user) {
        return UserResource.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public User toModel(UserResource resource) {
        return User.builder()
                .email(resource.getEmail())
                .name(resource.getName())
                .surname(resource.getSurname())
                .password(passwordEncoder.encode(resource.getPassword()))
                .roles(resource.getRoles())
                .build();
    }

    @Override
    public User update(User current, UserResource resource) {
        current.setEmail(resource.getEmail());
        current.setName(resource.getName());
        current.setSurname(resource.getSurname());
        if (resource.getPassword() != null)
            current.setPassword(passwordEncoder.encode(resource.getPassword()));
        if(userAgent.isAdmin())
            current.setRoles(resource.getRoles());
        return current;
    }
}
