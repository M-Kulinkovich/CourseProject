package com.itlab.group3.services.impl;

import com.itlab.group3.dao.LocationRepository;
import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.model.Location;
import com.itlab.group3.services.LocationService;
import com.itlab.group3.services.exceptions.DeleteException;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends AbstractCrudServiceImpl<Location> implements LocationService {

    private final ReportRecordRepository reportRecordRepository;

    public LocationServiceImpl(LocationRepository repository, ReportRecordRepository reportRecordRepository) {
        super(repository);
        this.reportRecordRepository = reportRecordRepository;
    }

    @Override
    public void delete(Location location) {
        if (reportRecordRepository.existsByLocation(location)) {
            throw new DeleteException("ReportRecord");
        }
        super.delete(location);
    }
}
