package com.itlab.group3.common.util;

import com.itlab.group3.common.exception.NotFoundException;
import com.itlab.group3.dao.model.AbstractEntity;

public class EntityUtil {
    public static void nullValid(AbstractEntity abstractEntity, String message) {
        if (abstractEntity == null)
            throw new NotFoundException(message);
    }
}
