package com.itlab.group3.services;

import com.itlab.group3.dao.model.Project;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.dao.model.UserAssignments;

import java.util.Optional;

public interface UserAssignmentsService extends CommonCrudService<UserAssignments> {

    Optional<UserAssignments> findByUser(User user);

    void deleteByUser(User user);

    UserAssignments assigning(User user, Project project, boolean isAssign);
}
