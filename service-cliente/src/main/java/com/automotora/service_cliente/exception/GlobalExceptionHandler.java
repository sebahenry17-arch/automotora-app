package com.automotora.service_cliente.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.automotora.service_cliente.config.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),              // código HTTP
                ex.getMessage(),                           // mensaje de la excepción
                request.getRequestURI(),                   // ruta del endpoint
                LocalDateTime.now().toString()             // timestamp en formato legible
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}