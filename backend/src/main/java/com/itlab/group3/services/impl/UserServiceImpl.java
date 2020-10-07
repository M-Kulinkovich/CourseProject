package com.itlab.group3.services.impl;

import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.ReportRepository;
import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.UserRepository;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.UserService;
import com.itlab.group3.services.exceptions.DeleteException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractCrudServiceImpl<User> implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final ReportRepository reportRepository;
    private final ReportRecordRepository reportRecordRepository;
    private final UserAssignmentsRepository userAssignmentsRepository;


    public UserServiceImpl(UserRepository repository, ReportRepository reportRepository, ReportRecordRepository reportRecordRepository, UserAssignmentsRepository userAssignmentsRepository) {
        super(repository);
        this.userRepository = repository;
        this.reportRepository = reportRepository;
        this.reportRecordRepository = reportRecordRepository;
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void delete(User user) {
        if (reportRepository.existsByUser(user)) {
            throw new DeleteException("Report");
        }
        if (reportRecordRepository.existsByUser(user)) {
            throw new DeleteException("ReportRecord");
        }
        if (userAssignmentsRepository.existsByUser(user)) {
            throw new DeleteException("UserAssignments");
        }
        super.delete(user);
    }
}
