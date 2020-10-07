package com.itlab.group3.services;

import com.itlab.group3.dao.*;
import com.itlab.group3.dao.model.*;
import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class TestDataInitializer {
    private TransactionTemplate transactionTemplate;

    private AboutRepository aboutRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private FeatureRepository featureRepository;
    private TaskRepository taskRepository;
    private DetailedTaskRepository detailedTaskRepository;
    private LocationRepository locationRepository;
    private ReportRepository reportRepository;
    private ReportDetailRepository reportDetailRepository;
    private ReportRecordRepository reportRecordRepository;
    private UserAssignmentsRepository userAssignmentsRepository;

    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initialize() {
        transactionTemplate.execute(transactionStatus -> {
            About about = About.builder()
                    .body("This is WTR test project")
                    .version("1.0")
                    .build();

            aboutRepository.save(about);

            Project project1 = saveProject("Warcraft 3.0", "Frozen chair");
            Project project2 = saveProject("Need for Speed", "Navy edition");
            Project project3 = saveProject("Half-life", "Hello from Zen");

            Feature feature1 =
                    saveFeature("Game menu", "Create menu logic with the following view and sound", project1);
            Feature feature2 =
                    saveFeature("Speedometer view", "Create speedometer view", project2);
            Feature feature3 =
                    saveFeature("Mount model", "Create shape, texture and animation", project3);

            Task task11 =
                    saveTask("Menu structure", "Create code according to UML scheme", feature1);
            Task task12 =
                    saveTask("Menu view", "Create menu view according to menu structure", feature1);
            Task task13 =
                    saveTask("Menu sound", "Create sound for menu", feature1);

            Task task21 =
                    saveTask("Speedometer view", "Create view for all cars", feature2);
            Task task22 =
                    saveTask("Speed dependence", "Add view dependence from actual speed", feature2);
            Task task23 =
                    saveTask("Nitro dependence", "Add view dependence from actual nitro amount", feature2);

            Task task31 =
                    saveTask("Mount shape", "Create shape of the mount", feature3);
            Task task32 =
                    saveTask("Mount texture", "Create texture for the created shape and link it", feature3);
            Task task33 =
                    saveTask("Mount animation", "Create 2 animations for mount : attack and block", feature3);

            DetailedTask detailedTask11 =
                    saveDetailedTask("Implement the UML menu scheme", task11);
            DetailedTask detailedTask12 =
                    saveDetailedTask("Menu must include background animation depending on game progress", task12);
            DetailedTask detailedTask13 =
                    saveDetailedTask("Create menu sound for each company of the game", task13);

            DetailedTask detailedTask21 =
                    saveDetailedTask("Create speedometer view for each car including nitro bar", task21);
            DetailedTask detailedTask22 =
                    saveDetailedTask("Add animation reflecting actual speed of the car", task22);
            DetailedTask detailedTask23 =
                    saveDetailedTask("Add animation reflecting actual nitro amount of the car", task23);

            DetailedTask detailedTask31 =
                    saveDetailedTask("Create mount shape using Compas program", task31);
            DetailedTask detailedTask32 =
                    saveDetailedTask("Crate red-colored texture for the mount and cover it", task32);
            DetailedTask detailedTask33 =
                    saveDetailedTask("Create animation for attacking and defending", task33);

            Location location1 = saveLocation("Brest");
            Location location2 = saveLocation("Minsk");

            User user1 =
                    saveUser("Gerald", "Rivia", "secret", "white_wolf@witcher.net", Role.USER);
            User user2 =
                    saveUser("Scorpion", "Underworld", "secret", "scorpion@mk.net", Role.USER);
            User user3 =
                    saveUser("Gordon", "Freeman", "adminSecret", "g-man@valve.net", Role.ADMIN);

            UserAssignments userAssignment1 = saveUserAssignments(user1, project1, project2, project3);
            UserAssignments userAssignment2 = saveUserAssignments(user2, project2, project3);
            UserAssignments userAssignment3 = saveUserAssignments(user3, project3);

            Report report1 =
                    saveReport(user1, LocalDate.of(2020, 02, 20), true, 8, 8);
            Report report2 =
                    saveReport(user2, LocalDate.of(2020, 02, 20), false, 8, 8);
            Report report3 =
                    saveReport(user3, LocalDate.of(2020, 02, 21), false, 8, 8);

            ReportDetail detail1 = saveReportDetail("Sounds have been created");
            ReportDetail detail2 = saveReportDetail("Speed animation is fixed");
            ReportDetail detail3 = saveReportDetail("Attack animation is changed");

            saveReportRecord(
                    report1,
                    user1,
                    project1,
                    feature1,
                    task13,
                    detailedTask13,
                    "Sounds have been created",
                    LocalDate.of(2020, 02, 20),
                    Factor.STANDARD,
                    8,
                    8,
                    location1
            );

            saveReportRecord(
                    report2,
                    user2,
                    project2,
                    feature2,
                    task23,
                    detailedTask23,
                    "Speed animation is fixed",
                    LocalDate.of(2020, 02, 20),
                    Factor.STANDARD,
                    8,
                    8,
                    location1
            );

            saveReportRecord(
                    report3,
                    user3,
                    project3,
                    feature3,
                    task33,
                    detailedTask33,
                    "Attack animation is changed",
                    LocalDate.of(2020, 02, 21),
                    Factor.STANDARD,
                    8,
                    8,
                    location1
            );

            return null;
        });
    }

    private Project saveProject(String name, String description) {
        Project project = Project.builder()
                .name(name) //NonNull, Unique, 1..100
                .description(description) //0..1000
                .build();
        return projectRepository.save(project);
    }

    private Feature saveFeature(String name, String description, Project project) {
        Feature feature = Feature.builder()
                .name(name) //NonNull, 1..100
                .description(description) //0..1000
                .project(project)
                .build();
        return featureRepository.save(feature);
    }

    private Task saveTask(String name, String description, Feature feature) {
        Task task = Task.builder()
                .name(name) //NonNull, 2..100
                .description(description) //0..1000
                .feature(feature)
                .build();
        return taskRepository.save(task);
    }

    private DetailedTask saveDetailedTask(String text, Task task) {
        DetailedTask detailedTask = DetailedTask.builder()
                .text(text) //0..2000
                .task(task)
                .build();
        return detailedTaskRepository.save(detailedTask);
    }

    private Location saveLocation(String name) {
        Location location = Location.builder()
                .name(name) // Unique, NonNull, 1..255
                .build();
        return locationRepository.save(location);
    }

    private User saveUser(String name, String surname, String password, String email, Role... roles) {
        List<Role> roleList = Collections.arrayToList(roles);

        User user = User.builder()
                .name(name) //NonNull, 1..128
                .surname(surname) //NonNull, 1..128
                .password(passwordEncoder.encode(password)) //NonNull, 8..256
                .email(email) //NonNull, Unique
                .roles(roleList)
                .build();
        return userRepository.save(user);
    }

    private UserAssignments saveUserAssignments(User user, Project... projects) {
        List<Project> projectList = Collections.arrayToList(projects);

        UserAssignments assignments = UserAssignments.builder()
                .user(user)
                .projects(projectList)
                .build();
        return userAssignmentsRepository.save(assignments);
    }

    private Report saveReport(User user, LocalDate date, boolean confirm, Integer hour, Integer workUnit) {
        Report report = Report.builder()
                .user(user)
                .date(date) //NonNull, yyyy-MM-dd
                .confirm(confirm) //NonNull
                .hour(hour * 60) //0..24*60
                .workUnit(workUnit * 60) //0..24*60
                .build();
        return reportRepository.save(report);
    }

    private ReportDetail saveReportDetail(String body) {
        ReportDetail reportDetail =
                ReportDetail.builder()
                        .body(body) // max 256
                        .build();
        return reportDetailRepository.save(reportDetail);
    }

    private ReportRecord saveReportRecord(
            Report report,
            User user,
            Project project,
            Feature feature,
            Task task,
            DetailedTask detailedTask,
            String text,
            LocalDate date,
            Factor factor,
            Integer hour,
            Integer workUnit,
            Location location
    ) {
        ReportRecord reportRecord = ReportRecord.builder()
                .report(report)
                .user(user)
                .project(project)
                .feature(feature)
                .task(task)
                .detailedTask(detailedTask)
                .text(text) //0..2000
                .date(date) //NonNull
                .factor(factor)
                .hour(hour * 60) //0..24*60
                .workUnit(workUnit * 60) //0..24*60
                .location(location)
                .build();
        return reportRecordRepository.save(reportRecord);
    }

}
