package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project extends AbstractEntity {

    @OneToMany(mappedBy = "project")
    private Collection<Feature> features;

    @Column(nullable = false, unique = true)
    @Size(min = 1, max = 100)
    private String name;

    @Column
    @Size(min = 0, max = 1000)
    private String description;

    @OneToMany(mappedBy = "project")
    private List<ReportRecord> reportRecords;
}
