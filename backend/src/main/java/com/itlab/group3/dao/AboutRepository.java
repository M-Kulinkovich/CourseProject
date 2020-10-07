package com.itlab.group3.dao;

import com.itlab.group3.dao.model.About;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutRepository extends CrudRepository<About, Long> {
}
