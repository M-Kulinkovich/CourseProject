package com.itlab.group3.controllers.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorInfo {
    private final String url;
    private final String ex;
}
