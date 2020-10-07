package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAssignments extends AbstractEntity {

    @OneToOne(optional = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Project> projects;

    public static UserAssignments create(User user) {
        return UserAssignments.builder()
                .user(user)
                .projects(new ArrayList<>())
                .build();
    }

    public boolean addProject(Project project) {
        if (!this.projects.contains(project)) {
            return this.projects.add(project);
        } else return false;
    }

    public boolean removeProject(Project project) {
        return this.projects.remove(project);
    }

}
