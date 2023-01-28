package com.karolis.bite.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private int code;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String message) {
        this.message = message;
    }

}
