package com.karolis.bite.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private Long status;
    private String message;
    private String suggestion;
    private @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime errorDate = LocalDateTime.now();

    public ApiError(Long status, String message, String suggestion) {
        this.status = status;
        this.message = message;
        this.suggestion = suggestion;
    }


}
