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

}
