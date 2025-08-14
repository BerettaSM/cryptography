package com.ramon.crypt.controller.exceptions;

import java.time.Instant;

import com.ramon.crypt.exceptions.ApplicationException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CustomError {

    private String message;
    private Integer status;
    private Instant timestamp;
    private String path;

    public CustomError(ApplicationException e) {
        message = e.getMessage();
        status = e.getHttpStatus().value();
        timestamp = Instant.now();
        path = e.getPath();
    }

    public static CustomError from(ApplicationException e) {
        return new CustomError(e);
    }
    
}
