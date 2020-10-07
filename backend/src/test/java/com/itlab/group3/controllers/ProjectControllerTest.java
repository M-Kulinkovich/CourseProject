package com.itlab.group3.controllers;

import com.itlab.group3.Group3Application;
import com.itlab.group3.dao.ProjectRepository;
import com.itlab.group3.dao.model.Project;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = Group3Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class ProjectControllerTest {

    private static final String URL = "/project";
    private static final String URL_ID = URL + "/{id}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProjectRepository projectRepository;

    Project createProject(String name, String description) {
        Project project = Project.builder().name(name).description(description).build();
        return projectRepository.save(project);
    }

    @Test
    void getById1() throws Exception {
        Project project = projectRepository.save(
                Project.builder()
                        .name(ProjectTestData.GET_ID_TEST_1.getName())
                        .description(ProjectTestData.GET_ID_TEST_1.getDescription())
                        .build()
        );

        mvc.perform(
                get(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(project.getId()))
                .andExpect(jsonPath("$.name").value(project.getName()))
                .andExpect(jsonPath("$.description").value(project.getDescription()));
    }

    @Test
    void getById12() throws Exception {
        Project project = projectRepository.save(
                Project.builder()
                        .name(ProjectTestData.GET_ID_TEST_2.getName())
                        .description(ProjectTestData.GET_ID_TEST_2.getDescription())
                        .build()
        );

        mvc.perform(
                get(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(project.getId()))
                .andExpect(jsonPath("$.name").value(project.getName()))
                .andExpect(jsonPath("$.description").value(project.getDescription()));
    }

    @Test
    void getByIdNotFound() throws Exception {
        Long id = 100L;
        while (projectRepository.existsById(id)) id++;
        mvc.perform(
                get(URL_ID, id)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void save1() throws Exception {
        String name = ProjectTestData.SAVE_TEST_1.getName();
        String description = ProjectTestData.SAVE_TEST_1.getDescription();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        if (description != null) {
            jsonObject.put("description", description);
        }
        MvcResult mvcResult =
                mvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObject.toString()))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(name))
                        .andExpect(jsonPath("$.description").value(description))
                        .andReturn();
        String s = mvcResult.getResponse().getContentAsString();
        Long id = new JSONObject(s).getLong("id");
        Project project = projectRepository.findById(id).orElse(null);
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
    }

    @Test
    void save2() throws Exception {
        String name = ProjectTestData.SAVE_TEST_2.getName();
        String description = ProjectTestData.SAVE_TEST_2.getDescription();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        if (description != null) {
            jsonObject.put("description", description);
        }
        MvcResult mvcResult =
                mvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonObject.toString()))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(name))
                        .andExpect(jsonPath("$.description").value(description))
                        .andReturn();
        String s = mvcResult.getResponse().getContentAsString();
        Long id = new JSONObject(s).getLong("id");
        Project project = projectRepository.findById(id).orElse(null);
        assertNotNull(project);
        assertEquals(name, project.getName());
        assertEquals(description, project.getDescription());
    }

    void testUpdate(String name, String description, String newName, String newDescription) throws Exception {
        Project project = createProject(name, description);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", newName);
        if (newDescription != null) {
            jsonObject.put("description", newDescription);
        }
        mvc.perform(
                put(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.description").value(newDescription))
                .andExpect(jsonPath("$.id").value(project.getId()));
        Project project1 = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(project1);
        assertEquals(project1.getName(), newName);
        assertEquals(project1.getDescription(), newDescription);
    }

    @Test
    void update1() throws Exception {
        Project project = projectRepository.save(
                Project.builder()
                        .name(ProjectTestData.UPDATE_TEST_1.getFirst().getName())
                        .description(ProjectTestData.UPDATE_TEST_1.getFirst().getDescription())
                        .build()
        );
        String newName = ProjectTestData.UPDATE_TEST_1.getSecond().getName();
        String newDescription = ProjectTestData.UPDATE_TEST_1.getSecond().getDescription();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", newName);
        if (newDescription != null) {
            jsonObject.put("description", newDescription);
        }
        mvc.perform(
                put(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.description").value(newDescription))
                .andExpect(jsonPath("$.id").value(project.getId()));
        Project project1 = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(project1);
        assertEquals(project1.getName(), newName);
        assertEquals(project1.getDescription(), newDescription);
    }

    @Test
    void update2() throws Exception {
        Project project = projectRepository.save(
                Project.builder()
                        .name(ProjectTestData.UPDATE_TEST_2.getFirst().getName())
                        .description(ProjectTestData.UPDATE_TEST_2.getFirst().getDescription())
                        .build()
        );
        String newName = ProjectTestData.UPDATE_TEST_2.getSecond().getName();
        String newDescription = ProjectTestData.UPDATE_TEST_2.getSecond().getDescription();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", newName);
        if (newDescription != null) {
            jsonObject.put("description", newDescription);
        }
        mvc.perform(
                put(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.description").value(newDescription))
                .andExpect(jsonPath("$.id").value(project.getId()));
        Project project1 = projectRepository.findById(project.getId()).orElse(null);
        assertNotNull(project1);
        assertEquals(project1.getName(), newName);
        assertEquals(project1.getDescription(), newDescription);
    }

    void testDelete(String name, String description) throws Exception {
        Project project = createProject(name, description);

        mvc.perform(
                delete(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(projectRepository.existsById(project.getId()));
    }

    @Test
    void delete1() throws Exception {
        Project project = projectRepository.save(
                Project.builder()
                        .name(ProjectTestData.DELETE_TEST_1.getName())
                        .description(ProjectTestData.DELETE_TEST_1.getDescription())
                        .build()
        );

        mvc.perform(
                delete(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(204));
        assertFalse(projectRepository.existsById(project.getId()));
    }

    @Test
    void delete2() throws Exception {
        Project project = projectRepository.save(
                Project.builder()
                        .name(ProjectTestData.DELETE_TEST_2.getName())
                        .description(ProjectTestData.DELETE_TEST_2.getDescription())
                        .build()
        );

        mvc.perform(
                delete(URL_ID, project.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(204));
        assertFalse(projectRepository.existsById(project.getId()));
    }

    @Test
    void getAll() throws Exception {
        projectRepository.deleteAll();
        projectRepository.saveAll(
                ProjectTestData.GET_ALL_TEST.stream()
                        .map(
                                p -> Project.builder()
                                        .name(p.getName())
                                        .description(p.getDescription())
                                        .build())
                        .collect(Collectors.toList())
        );

        int count = (int) projectRepository.count();
        mvc.perform(
                get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(count)));
    }

    @Test
    void getEmptyAll() throws Exception {
        projectRepository.deleteAll();
        mvc.perform(
                get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}