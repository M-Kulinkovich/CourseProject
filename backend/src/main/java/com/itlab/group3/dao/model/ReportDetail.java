package com.itlab.group3.dao.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetail extends AbstractEntity {
    @Column
    @Length(max = 256)
    private String body;
}
