package com.ramon.crypt.controller.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ramon.crypt.controller.exceptions.CustomError;
import com.ramon.crypt.exceptions.ApplicationException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomError> catchAll(ApplicationException e) {
        CustomError err = CustomError.from(e);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

}
