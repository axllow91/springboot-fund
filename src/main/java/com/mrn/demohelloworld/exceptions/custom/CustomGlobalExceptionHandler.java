package com.mrn.demohelloworld.exceptions.custom;

import com.mrn.demohelloworld.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // MethodArgumentNotValidException
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
                "From MethodArgumentNotValidException in GEH",
                ex.getLocalizedMessage());

        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

    }

    // HttpRequestMethodNotSupportedException
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
                "From HttpRequestMethodNotSupportedException in GEH - Method Not Allowed (405)",
                ex.getLocalizedMessage());

        return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);

    }

    // UsernameNotFoundException custom handling
    @ExceptionHandler(UsernameNotFoundException.class)
    protected final ResponseEntity<Object> handleUsernameNotFoundException(
            UsernameNotFoundException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
                ex.getMessage(), request.getDescription(false)); // false does not include the client details

        return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
    }

    // ConstraintsViolationException (when we are going to pass in uri bad constraints (example: id's)
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintsViolationException(ConstraintViolationException ex,
                                                                            WebRequest request) {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
                ex.getMessage(), request.getDescription(false)); // false does not include the client details

        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }

}
