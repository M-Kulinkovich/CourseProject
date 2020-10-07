package com.itlab.group3.dao.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report extends AbstractEntity {

    @OneToMany(mappedBy = "report", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    List<ReportRecord> reportRecords;

    @ManyToOne(optional = false)
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean confirm;

    @Column
    private Integer hour;

    @Column
    private Integer workUnit;

}
