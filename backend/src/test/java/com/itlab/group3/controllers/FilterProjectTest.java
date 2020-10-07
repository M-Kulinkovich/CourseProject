package com.itlab.group3.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itlab.group3.Group3Application;
import com.itlab.group3.controllers.mappers.ProjectMapper;
import com.itlab.group3.dao.ProjectRepository;
import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.UserRepository;
import com.itlab.group3.dao.model.Project;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.dao.model.UserAssignments;
import com.itlab.group3.services.ProjectService;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.itlab.group3.dao.model.Role.ADMIN;
import static com.itlab.group3.dao.model.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Group3Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class FilterProjectTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserAssignmentsRepository userAssignmentsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @SneakyThrows
    @Test
    void test() {
        Project project1 = projectRepository.save(Project.builder().name("project 1").build());
        Project project2 = projectRepository.save(Project.builder().name("project 2").build());
        User user1 = userRepository.save(User.builder()
                .name("user 1")
                .roles(Lists.list(ADMIN))
                .email("projectTest@test.test")
                .surname("surname user 1")
                .password("password test")
                .active(true)
                .build()
        );

        User user2 = userRepository.save(User.builder()
                .name("user 2")
                .roles(Lists.list(USER))
                .email("projectTest2@test2.test")
                .surname("surname user2")
                .password("test password 2")
                .active(true)
                .build()
        );

        UserAssignments userAssignments1 = userAssignmentsRepository.save(
                UserAssignments.builder()
                        .user(user1)
                        .projects(Lists.list(project1))
                        .build()
        );
//        UserAssignments userAssignments2 = userAssignmentsRepository.save(
//                UserAssignments.builder()
//                        .user(user2)
//                        .projects(Lists.list(project2))
//                        .build()
//        );

        List<Project> projects = projectService.findAll(null, user1);
        assertEquals(projects.size(), 1);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(projectMapper.toResource(projects)));
//        System.out.println(projects.);

    }

}
