package com.itlab.group3.services;

import com.itlab.group3.dao.model.User;

import java.util.Optional;

public interface UserService extends CommonCrudService<User> {
    Optional<User> findByEmail(String email);
}
