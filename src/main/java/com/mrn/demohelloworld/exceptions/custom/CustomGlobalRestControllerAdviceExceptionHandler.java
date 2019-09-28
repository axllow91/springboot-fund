package com.mrn.demohelloworld.exceptions.custom;

import com.mrn.demohelloworld.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

//@RestControllerAdvice
public class CustomGlobalRestControllerAdviceExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomErrorDetails usernameNotFound(UsernameNotFoundException ex) {
        return new CustomErrorDetails(new Date(), "From @RestControllerAdvice NOT_FOUND (404)",
                ex.getMessage());
    }

}
