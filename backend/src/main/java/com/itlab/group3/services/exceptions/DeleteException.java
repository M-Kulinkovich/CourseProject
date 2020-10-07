package com.itlab.group3.services.exceptions;

public class DeleteException extends RuntimeException {

    public DeleteException(String modelName) {
        super("deletion is not available, because there is a link from '" + modelName + "'");
    }

}
