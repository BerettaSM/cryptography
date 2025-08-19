package com.ramon.crypt.controller.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ramon.crypt.controller.exceptions.CustomError;
import com.ramon.crypt.controller.exceptions.CustomValidationError;
import com.ramon.crypt.exceptions.ApplicationException;
import com.ramon.crypt.util.PathUtils;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<CustomError> catchAll(ApplicationException e) {
        CustomError err = CustomError.from(e);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> validationError(MethodArgumentNotValidException e) {
        CustomValidationError err = CustomValidationError.from(e);
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomError> invalidEndpoint(NoResourceFoundException e) {
        CustomError err = new CustomError(
            "Invalid endpoint",
            HttpStatus.NOT_FOUND.value(),
            Instant.now(),
            PathUtils.getCurrentPath());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomError> invalidEndpoint(MethodArgumentTypeMismatchException e) {
        CustomError err = new CustomError(
            "Path variable type mismatch",
            HttpStatus.BAD_REQUEST.value(),
            Instant.now(),
            PathUtils.getCurrentPath());
        return ResponseEntity.status(err.getStatus()).body(err);
    }

}
