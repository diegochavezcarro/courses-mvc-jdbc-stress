package com.diegochavez.courses.controller;

import com.diegochavez.courses.model.ApiErrorCode;
import com.diegochavez.courses.model.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            ServerWebExchange exchange
    ) {
        return buildError(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.VALIDATION_ERROR,
                exception.getMessage(),
                exchange
        );
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ApiErrorResponse> handleInputException(
            ServerWebInputException exception,
            ServerWebExchange exchange
    ) {
        String message = exception.getReason() != null ? exception.getReason() : "Invalid request";
        return buildError(
                HttpStatus.BAD_REQUEST,
                ApiErrorCode.BAD_REQUEST,
                message,
                exchange
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
            Exception exception,
            ServerWebExchange exchange
    ) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ApiErrorCode.INTERNAL_ERROR,
                "Unexpected error",
                exchange
        );
    }

    private ResponseEntity<ApiErrorResponse> buildError(
            HttpStatus status,
            ApiErrorCode code,
            String message,
            ServerWebExchange exchange
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                code.name(),
                message,
                OffsetDateTime.now(),
                exchange.getRequest().getPath().value()
        );
        return ResponseEntity.status(status).body(response);
    }
}
