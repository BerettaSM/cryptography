package com.ramon.crypt.controller.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.ramon.crypt.util.PathUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomValidationError extends CustomError {

    private final List<FieldErrorEntry> errors = new ArrayList<>();

    public CustomValidationError(Integer status, String message, Instant timestamp, String path, List<FieldErrorEntry> errors) {
        super(message, status, timestamp, path);
        this.errors.addAll(errors);
    }

    public static CustomValidationError from(MethodArgumentNotValidException e) {
        List<FieldErrorEntry> errors = extractErrors(e);
        return new CustomValidationError(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Validation error(s).",
            Instant.now(),
            PathUtils.getCurrentPath(),
            errors);
    }

    private static List<FieldErrorEntry> extractErrors(MethodArgumentNotValidException e) {
        return e.getFieldErrors()
            .stream()
            .collect(
                Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        FieldError::getDefaultMessage,
                        Collectors.toList())))
            .entrySet()
            .stream()
            .map(FieldErrorEntry::new)
            .toList();
    }

}
