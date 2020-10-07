package com.itlab.group3.services.impl;

import com.itlab.group3.dao.ReportDetailRepository;
import com.itlab.group3.dao.model.ReportDetail;
import com.itlab.group3.services.ReportDetailService;
import org.springframework.stereotype.Service;

@Service
public class ReportDetailServiceImpl
        extends AbstractCrudServiceImpl<ReportDetail>
        implements ReportDetailService {

    public ReportDetailServiceImpl(ReportDetailRepository repository) {
        super(repository);
    }
}
