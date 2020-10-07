package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailedTask extends AbstractEntity {

    @Column
    private String text;

    @OneToOne(optional = false)
    @JoinColumn
    private Task task;

    @OneToMany(mappedBy = "detailedTask")
    private List<ReportRecord> reportRecords;

}
