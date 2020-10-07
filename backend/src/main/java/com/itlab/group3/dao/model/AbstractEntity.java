package com.itlab.group3.dao.model;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity implements Serializable {

    @Column(nullable = false, unique = true)
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
