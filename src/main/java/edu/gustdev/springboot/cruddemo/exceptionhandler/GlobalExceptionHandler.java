package edu.gustdev.springboot.cruddemo.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.NoResultException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<String> noResultExceptionHandler() {
        return new ResponseEntity<String>("Employee was not found in this company.", HttpStatus.NOT_FOUND);
    }
}
