package com.itlab.group3.services.impl;

import com.itlab.group3.dao.AboutRepository;
import com.itlab.group3.dao.model.About;
import com.itlab.group3.services.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AboutServiceImpl implements AboutService {

    @NonNull
    private final AboutRepository aboutRepository;

    @Override
    public Optional<About> getAbout() {
        Iterable<About> all = aboutRepository.findAll();
        return all.iterator().hasNext() ? Optional.of(all.iterator().next()) : Optional.empty();
    }
}
