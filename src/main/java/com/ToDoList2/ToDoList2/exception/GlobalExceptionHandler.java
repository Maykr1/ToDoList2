package com.ToDoList2.ToDoList2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("[ RESOURCE_NOT_FOUND_EXCEPTION ] - An error occured: {}", e.getMessage());    
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
