package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRecord extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    private Report report;

    @ManyToOne(optional = false)
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Project project;

    @ManyToOne
    @JoinColumn
    private Feature feature;

    @ManyToOne
    @JoinColumn
    private Task task;

    @ManyToOne
    @JoinColumn
    private DetailedTask detailedTask;

    @Column
    @Size(min = 0, max = 2000)
    private String text;

    @Column(nullable = false)
    private LocalDate date;

    @JoinColumn
    @Enumerated(EnumType.STRING)
    private Factor factor;

    @Column
    private Integer hour;

    @Column
    private Integer workUnit;

    @ManyToOne(optional = false)
    private Location location;


}
