package com.itlab.group3.controllers.exception;

import com.itlab.group3.common.exception.NotFoundException;
import com.itlab.group3.common.exception.WtrException;
import com.itlab.group3.services.exceptions.DeleteException;
import com.itlab.group3.services.exceptions.UpdateException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorInfo(request.getRequestURL().toString(), ex.getMessage()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorInfo> handleJwtException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorInfo(request.getRequestURL().toString(), ex.getMessage()));
    }

    @ExceptionHandler({WtrException.class, Exception.class})
    public ResponseEntity<ErrorInfo> handleException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorInfo(request.getRequestURL().toString(), ex.getMessage()));
    }

    @ExceptionHandler({ValidException.class})
    public ResponseEntity<ErrorInfo> handleValidException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorInfo(request.getRequestURL().toString(), "not valid"));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorInfo(((ServletWebRequest) request).getRequest().getRequestURL().toString(),
                        "entity not found or parameter cannot be processed"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorInfo(((ServletWebRequest) request).getRequest().getRequestURL().toString(),
                        "not valid"));
    }

    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<ErrorInfo> handleForbidden(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorInfo(request.getRequestURL().toString(), ex.getMessage()));
    }


    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ErrorInfo> handleUnprocessableEntityException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorInfo(request.getRequestURL().toString(), ex.getMessage()));
    }


}
