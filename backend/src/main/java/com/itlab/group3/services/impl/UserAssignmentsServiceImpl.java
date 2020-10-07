package com.itlab.group3.services.impl;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Project;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.dao.model.UserAssignments;
import com.itlab.group3.services.UserAssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAssignmentsServiceImpl
        extends AbstractCrudServiceImpl<UserAssignments>
        implements UserAssignmentsService {

    private final UserAssignmentsRepository userAssignmentsRepository;

    @Autowired
    public UserAssignmentsServiceImpl(UserAssignmentsRepository repository) {
        super(repository);
        this.userAssignmentsRepository = repository;
    }

    @Override
    public Optional<UserAssignments> findByUser(User user) {
        return userAssignmentsRepository.findByUser(user);
    }

    @Override
    public void deleteByUser(User user) {
        Optional<UserAssignments> userAssignments = findByUser(user);
        userAssignments.ifPresent(userAssignmentsRepository::delete);
    }

    @Override
    public UserAssignments assigning(User user, Project project, boolean isAssign) {
        UserAssignments userAssignment = findByUser(user)
                .orElse(UserAssignments.create(user));
        if (isAssign) {
            userAssignment.addProject(project);
        } else {
            userAssignment.removeProject(project);
        }

        return userAssignmentsRepository.save(userAssignment);
    }

}
