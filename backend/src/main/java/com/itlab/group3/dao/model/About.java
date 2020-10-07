package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class About extends AbstractEntity {

    @Column
    private String body;

    @Column
    private String version;
}
