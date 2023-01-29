package com.karolis.bite.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> existingEmailExceptionHandler(ConflictException exception){
        return ResponseEntity.status(422).body(new ApiError(409L, exception.getMessage(), "Try another email"));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFoundExceptionHandler(NotFoundException exception){
        return ResponseEntity.status(404).body(new ApiError(404L, exception.getMessage(), "Double check your parameters"));
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ApiError> propertyValueExceptionHandler(PropertyValueException exception){
        return ResponseEntity.status(422).body(new ApiError(422L, exception.getMessage(), "You are missing parameters, make sure you add all required parameters."));
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ApiError> serverErrorExceptionHandler(ServerErrorException exception){
        return ResponseEntity.status(500).body(new ApiError(500L, exception.getMessage(), "Try again later or call support."));
    }

}
