package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.model.Report;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;

import java.time.LocalDate;

import static com.itlab.group3.services.filter.ConditionSet.ConditionElement.elementOf;

public class ReportFilter extends Filter<Report> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(
            elementOf("userId", "user.id", Long.class, Comparison.eq),
            elementOf("date", "date", LocalDate.class, Comparison.eq, Comparison.lt, Comparison.lte, Comparison.gt, Comparison.gte),
            elementOf("confirm", "confirm", Boolean.class, Comparison.eq),
            elementOf("projectId", "reportRecords.project.id", Long.class, Comparison.eq),
            elementOf("featureId", "reportRecords.feature.id", Long.class, Comparison.eq),
            elementOf("taskId", "reportRecords.task.id", Long.class, Comparison.eq)
    );

    public ReportFilter(String filterString) {
        super(filterString, CONDITIONS);
    }

    public ReportFilter(String externalName, Comparison comparison, String value) {
        super(externalName, comparison, value, CONDITIONS);
    }

    public static ReportFilter userIdFilter(Long userId) {
        return new ReportFilter("userId", Comparison.eq, userId.toString());
    }

}
