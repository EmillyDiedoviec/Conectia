package com.conectia.Conectia.configs;

import com.conectia.Conectia.Dtos.ErrorData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity dataValidationError(MethodArgumentNotValidException exception){

        var errors = exception.getFieldErrors().stream().map(
                (error) -> new ErrorData(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

}