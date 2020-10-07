package com.itlab.group3.dao.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location extends AbstractEntity {

    @Size(min = 1, max = 255)
    @Column(unique = true, nullable = false)
    private String name;

}
