package com.itlab.group3.services.impl;

import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.model.ReportRecord;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.ReportRecordService;
import com.itlab.group3.services.exceptions.UpdateException;
import com.itlab.group3.services.filter.filters.ReportRecordFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class ReportRecordServiceImpl extends AbstractCrudServiceImpl<ReportRecord> implements ReportRecordService {

    private final ReportRecordRepository reportRecordRepository;

    public ReportRecordServiceImpl(ReportRecordRepository repository) {
        super(repository);
        reportRecordRepository = repository;
    }

    @Override
    public JpaSpecificationExecutor<ReportRecord> getRepository() {
        return reportRecordRepository;
    }

    @Override
    public ReportRecord update(ReportRecord reportRecord, User user) {
        if (!user.isAdmin() && (
                !reportRecord.getUser().getId().equals(user.getId()) || (
                        reportRecord.getReport() != null &&
                                reportRecord.getReport().getConfirm()
                )
        )
        ) {
            throw new UpdateException();
        }
        return reportRecordRepository.save(reportRecord);
    }

    @Override
    public Specification<ReportRecord> getUserAdditionalSpecification(User user) {
        return ReportRecordFilter.userIdFilter(user.getId()).toSpecification();
    }
}
