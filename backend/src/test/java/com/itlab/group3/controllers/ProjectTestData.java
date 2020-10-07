package com.itlab.group3.controllers;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ProjectTestData {
    public static final ProjectTest SAVE_TEST_1 = new ProjectTest("save1", "description test save");
    public static final ProjectTest SAVE_TEST_2 = new ProjectTest("save2", null);

    public static final ProjectTest GET_ID_TEST_1 = new ProjectTest("getById1", "description test get by id");
    public static final ProjectTest GET_ID_TEST_2 = new ProjectTest("getById2", null);

    public static final ProjectPair UPDATE_TEST_1 = new ProjectPair(
            new ProjectTest("update1", "description update test"),
            new ProjectTest("new update1", "new description update test")
    );

    public static final ProjectPair UPDATE_TEST_2 = new ProjectPair(
            new ProjectTest("update2", null),
            new ProjectTest("new update2", "new description update ")
    );

    public static final ProjectTest DELETE_TEST_1 = new ProjectTest("delete1", "description delete test");
    public static final ProjectTest DELETE_TEST_2 = new ProjectTest("delete2", null);

    public static final List<ProjectTest> GET_ALL_TEST = Arrays.asList(
            new ProjectTest("all test project1", "description 1"),
            new ProjectTest("all test project2", null)
    );

    @AllArgsConstructor
    @Getter
    public static class ProjectTest {
        private String name;
        private String description;
    }

    @AllArgsConstructor
    @Getter
    public static class ProjectPair {
        private ProjectTest first;
        private ProjectTest second;
    }


}
