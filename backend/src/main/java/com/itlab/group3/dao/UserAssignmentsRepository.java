package com.itlab.group3.dao;

import com.itlab.group3.dao.model.User;
import com.itlab.group3.dao.model.UserAssignments;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAssignmentsRepository extends CommonCrudRepository<UserAssignments> {

    Optional<UserAssignments> findByUser(User user);

    Optional<UserAssignments> findByUserId(Long id);

    boolean existsByUser(User user);
}
