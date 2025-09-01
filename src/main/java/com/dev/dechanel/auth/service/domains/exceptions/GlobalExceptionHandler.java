package com.dev.dechanel.auth.service.domains.exceptions;

import com.dev.dechanel.auth.service.domains.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        LOGGER.error("Handling MethodArgumentNotValidException: {}", ex.getMessage());

        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::buildFieldErrorMessage)
                .collect(Collectors.toList());

        String errorMessage = "Validation failed: " + String.join("; ", fieldErrors);

        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(errorMessage)
                .timestamp(LocalDateTime.now().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> handleErrorException(ErrorException ex) {
        LOGGER.error("Handling ErrorException: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        LOGGER.error("Handling NotFoundException: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex) {
        LOGGER.error("Handling ConflictException: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        LOGGER.error("Handling UnauthorizedException: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        LOGGER.error("Handling Generic Exception: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred. Please try again later.")
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidUUIDException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUUIDException(InvalidUUIDException ex) {
        LOGGER.error("Handling InvalidUUIDException: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(ex.getTimestamp().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        LOGGER.error("Handling MethodArgumentTypeMismatchException: {}", ex.getMessage(), ex);
        String message = String.format("Invalid value '%s' for parameter '%s'. Expected type is '%s'.",
                ex.getValue(),
                ex.getName(),
                Objects.nonNull(ex.getRequiredType()) ? ex.getRequiredType().getSimpleName() : "unknown");

        final  var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .timestamp(LocalDateTime.now().toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.error("Handling IllegalArgumentException: {}", ex.getMessage(), ex);
        final var errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String buildFieldErrorMessage(FieldError error) {
        return String.format("Field '%s' %s (rejected value: %s)",
                error.getField(),
                error.getDefaultMessage(),
                Objects.isNull(error.getRejectedValue()) ? "null" : error.getRejectedValue().toString());
    }

}
