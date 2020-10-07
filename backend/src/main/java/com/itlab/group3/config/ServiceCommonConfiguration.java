package com.itlab.group3.config;

import com.itlab.group3.dao.*;
import com.itlab.group3.services.TestDataInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ServiceCommonConfiguration {
    @NonNull
    private final TransactionTemplate transactionTemplate;

    @NonNull
    private final AboutRepository aboutRepository;
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final ProjectRepository projectRepository;
    @NonNull
    private final FeatureRepository featureRepository;
    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final DetailedTaskRepository detailedTaskRepository;
    @NonNull
    private final LocationRepository locationRepository;
    @NonNull
    private final ReportRepository reportRepository;
    @NonNull
    private final ReportDetailRepository reportDetailRepository;
    @NonNull
    private final ReportRecordRepository reportRecordRepository;
    @NonNull
    private final UserAssignmentsRepository userAssignmentsRepository;

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Profile("initdb")
    @Bean
    TestDataInitializer testDataInitializer() {
        return new TestDataInitializer(
                transactionTemplate,

                aboutRepository,
                userRepository,
                projectRepository,
                featureRepository,
                taskRepository,
                detailedTaskRepository,
                locationRepository,
                reportRepository,
                reportDetailRepository,
                reportRecordRepository,
                userAssignmentsRepository,

                passwordEncoder
        );
    }
}
