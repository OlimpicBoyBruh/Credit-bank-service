package ru.bank.jd.controller;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handlerArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage("Ошибка: " + exception.getMessage()));
    }
}
