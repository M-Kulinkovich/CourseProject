package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends AbstractEntity {

    @Column(nullable = false)
    @Size(min = 1, max = 100)
    private String name;

    @Column
    @Size(min = 0, max = 1000)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn
    private Feature feature;

    @OneToOne(mappedBy = "task", cascade = CascadeType.REMOVE)
    private DetailedTask detailedTasks;

    @OneToMany(mappedBy = "task")
    private List<ReportRecord> reportRecords;
}
