package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;

public class UserFilter extends Filter<User> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(

    );

    public UserFilter(String filterString, ConditionSet conditions) {
        super(filterString, CONDITIONS);
    }
}
