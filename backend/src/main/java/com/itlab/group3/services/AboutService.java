package com.itlab.group3.services;

import com.itlab.group3.dao.model.About;

import java.util.Optional;

public interface AboutService {

    Optional<About> getAbout();
}
