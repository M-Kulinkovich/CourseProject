package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.model.ReportRecord;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;

import java.time.LocalDate;

import static com.itlab.group3.services.filter.ConditionSet.ConditionElement.elementOf;

public class ReportRecordFilter extends Filter<ReportRecord> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(
            elementOf("reportId", "report.id", Long.class, Comparison.eq),
            elementOf("report", "report", String.class, Comparison.isn, Comparison.nn),
            elementOf("userId", "user.id", Long.class, Comparison.eq),
            elementOf("date", "date", LocalDate.class, Comparison.eq, Comparison.lt, Comparison.lte, Comparison.gt, Comparison.gte),
            elementOf("projectId", "project.id", Long.class, Comparison.eq),
            elementOf("featureId", "feature.id", Long.class, Comparison.eq),
            elementOf("taskId", "task.id", Long.class, Comparison.eq),
            elementOf("factor", "factor", String.class, Comparison.eq)
    );

    public ReportRecordFilter(String filterString) {
        super(filterString, CONDITIONS);
    }

    public ReportRecordFilter(String externalName, Comparison comparison, String value) {
        super(externalName, comparison, value, CONDITIONS);
    }

    public static ReportRecordFilter userIdFilter(Long userId) {
        return new ReportRecordFilter("userId", Comparison.eq, userId.toString());
    }

}
