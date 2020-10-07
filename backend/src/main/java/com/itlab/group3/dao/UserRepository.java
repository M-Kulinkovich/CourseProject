package com.itlab.group3.dao;

import com.itlab.group3.dao.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CommonCrudRepository<User> {
    Optional<User> findByEmail(String email);
}
