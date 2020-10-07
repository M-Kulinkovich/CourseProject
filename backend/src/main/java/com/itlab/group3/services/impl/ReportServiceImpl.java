package com.itlab.group3.services.impl;

import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.ReportRepository;
import com.itlab.group3.dao.model.Report;
import com.itlab.group3.dao.model.ReportRecord;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.ReportService;
import com.itlab.group3.services.exceptions.UpdateException;
import com.itlab.group3.services.filter.filters.ReportFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl extends AbstractCrudServiceImpl<Report> implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportRecordRepository reportRecordRepository;

    public ReportServiceImpl(ReportRepository repository, ReportRecordRepository reportRecordRepository) {
        super(repository);
        reportRepository = repository;
        this.reportRecordRepository = reportRecordRepository;
    }

    @Override
    public JpaSpecificationExecutor<Report> getRepository() {
        return reportRepository;
    }

    @Override
    public Report update(Report report, User user) {
        if (user.isAdmin() || (report.getUser().getId().equals(user.getId()) && !report.getConfirm())) {
            return save(report);
        }
        throw new UpdateException();
    }

    @Override
    public Report confirm(Report report, User currentUser, boolean confirm) {
        if (!currentUser.isAdmin() && (!confirm || !report.getUser().getId().equals(currentUser.getId()))) {
            throw new UpdateException();
        }
        report.setConfirm(confirm);
        return reportRepository.save(report);
    }

    @Override
    public Specification<Report> getUserAdditionalSpecification(User user) {
        return ReportFilter.userIdFilter(user.getId()).toSpecification();
    }

    @Override
    @Transactional
    public Report save(Report report) {
        List<ReportRecord> oldReportRecords = reportRecordRepository.findAllByReportId(report.getId());
        for (ReportRecord reportRecord : oldReportRecords) {
            reportRecord.setReport(null);
        }
        for (ReportRecord reportRecord : report.getReportRecords()) {
            reportRecord.setReport(report);
        }
        List<ReportRecord> deleteReportRecords = new ArrayList<>();
        for (ReportRecord reportRecord : oldReportRecords) {
            if (reportRecord.getReport() == null) {
                deleteReportRecords.add(reportRecord);
            }
        }
        reportRecordRepository.deleteAll(deleteReportRecords);
        return super.save(report);
    }
}
