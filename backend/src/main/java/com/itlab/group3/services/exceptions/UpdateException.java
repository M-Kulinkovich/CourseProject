package com.itlab.group3.services.exceptions;

public class UpdateException extends RuntimeException {

    public UpdateException() {
        super("the update is not available");
    }
}
